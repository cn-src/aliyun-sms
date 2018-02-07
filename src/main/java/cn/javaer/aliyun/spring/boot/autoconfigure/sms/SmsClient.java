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

/**
 * @author zhangpeng
 */
public class SmsClient {

    private final IAcsClient acsClient;
    private final AliyunSmsProperties smsProperties;

    public SmsClient(final IAcsClient acsClient, final AliyunSmsProperties smsProperties) {
        this.acsClient = acsClient;
        this.smsProperties = smsProperties;
    }

    public void sendAuthCode(final String phoneNumber, final String code) {
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
            if (!"OK".equalsIgnoreCase(response.getCode())) {
                throw new SmsSendException("Response code is '" + response.getCode() + "'");
            }
        } catch (final ClientException e) {
            throw new SmsSendException(e);
        }
    }
}
