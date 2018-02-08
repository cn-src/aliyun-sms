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

package cn.javaer.aliyun.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author zhangpeng
 */
public class SmsClient {

    private static final String SUCCESS_CODE = "OK";
    private static final Random RANDOM = new Random();
    private static final String PHONE_NUMBER_REGEX = "((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}";
    private final IAcsClient acsClient;
    private final String product = "Dysmsapi";
    private final String domain = "dysmsapi.aliyuncs.com";
    private final String region = "cn-hangzhou";
    private final String endpointName = "cn-hangzhou";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private SmsTemplate.Builder authenticationSmsTemplateBuilder;

    /**
     * Instantiates a new SmsClient.
     *
     * @param accessKeyId 阿里云短信 accessKeyId
     * @param accessKeySecret 阿里云短信 accessKeySecret
     */
    public SmsClient(final String accessKeyId, final String accessKeySecret) {
        final IClientProfile clientProfile = DefaultProfile.getProfile(
                this.region, accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint(this.endpointName, this.region, this.product, this.domain);
        } catch (final ClientException e) {
            throw new RuntimeException(e);
        }
        this.acsClient = new DefaultAcsClient(clientProfile);
    }

    /**
     * 生成随机数.
     *
     * @param startInclusive 随机范围起始数字（包含）
     * @param endExclusive 随机范围结束数字（不包含）
     *
     * @return 随机数
     */
    private static int nextInt(final int startInclusive, final int endExclusive) {

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
        final int code = nextInt(100000, 1000000);
        send(this.authenticationSmsTemplateBuilder.addTemplateParam("code", String.valueOf(code)).build(), phoneNumber);
        return code;
    }

    public void send(final SmsTemplate smsTemplate, final String... phoneNumbers) {
        final SendSmsRequest request = new SendSmsRequest();
        request.setMethod(MethodType.POST);
        request.setPhoneNumbers(Arrays.stream(phoneNumbers).collect(Collectors.joining(",")));
        request.setSignName(smsTemplate.getSignName());
        request.setTemplateCode(smsTemplate.getTemplateCode());
        try {
            final String param = this.objectMapper.writeValueAsString(smsTemplate.getTemplateParam());
            request.setTemplateParam(param);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }

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
    }

    public void setAuthenticationSmsTemplate(final SmsTemplate authenticationSmsTemplate) {
        this.authenticationSmsTemplateBuilder = authenticationSmsTemplate.toBuilder();
    }
}
