/*
 * Copyright (c) 2018 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.javaer.aliyun.spring.boot.autoconfigure.sms;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import org.springframework.util.Assert;

import java.util.Random;

/**
 * @author zhangpeng
 */
public class SmsClient {

    public static final String SUCCESS_CODE = "OK";
    private static final Random RANDOM = new Random();
    private static final String PHONE_NUMBER_REGEX = "((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}";
    private final IAcsClient acsClient;
    private final AliyunSmsProperties smsProperties;

    /**
     * Instantiates a new SmsClient.
     *
     * @param acsClient the acs client
     * @param smsProperties the sms properties
     */
    public SmsClient(final IAcsClient acsClient, final AliyunSmsProperties smsProperties) {
        this.acsClient = acsClient;
        this.smsProperties = smsProperties;
    }

    /**
     * 生成随机数.
     *
     * @param startInclusive 随机范围起始数字（包含）
     * @param endExclusive 随机范围结束数字（不包含）
     *
     * @return 随机数
     */
    public static int nextInt(final int startInclusive, final int endExclusive) {

        if (endExclusive < startInclusive) {
            throw new IllegalArgumentException("Start value must be smaller or equal to end value.");
        }
        if (startInclusive < 0) {
            throw new IllegalArgumentException("Both range values must be non-negative.");
        }

        if (startInclusive == endExclusive) {
            return startInclusive;
        }

        return startInclusive + RANDOM.nextInt(endExclusive - startInclusive);
    }

    /**
     * 发送身份验证验证码.
     *
     * @param phoneNumber 中国手机号码
     *
     * @return 6 位数的随机码
     */
    public int sendAuthenticationCode(final String phoneNumber) {
        Assert.hasText(phoneNumber, "'phoneNumber' must not be empty");
        if (!phoneNumber.matches(PHONE_NUMBER_REGEX)) {
            throw new IllegalArgumentException("'phoneNumber' is not a chinese mobile phone number");
        }

        final int code = nextInt(100000, 1000000);
        final SendSmsRequest request = new SendSmsRequest();
        request.setMethod(MethodType.POST);
        request.setPhoneNumbers(phoneNumber);
        request.setSignName(this.smsProperties.getSignName());
        request.setTemplateCode(this.smsProperties.getAuthTemplateCode());
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        try {
            final SendSmsResponse response = this.acsClient.getAcsResponse(request);
            if (null == response) {
                throw new SmsSendException("Response is null");
            }
            if (!SUCCESS_CODE.equalsIgnoreCase(response.getCode())) {
                throw new SmsSendException("Response code is '" + response.getCode() + "'");
            }
        } catch (final ClientException e) {
            throw new SmsSendException(e);
        }
        return code;
    }
}
