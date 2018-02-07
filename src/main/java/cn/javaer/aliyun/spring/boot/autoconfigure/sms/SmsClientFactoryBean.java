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

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author zhangpeng
 */
public class SmsClientFactoryBean implements FactoryBean<SmsClient>, InitializingBean {

    private AliyunSmsProperties smsProperties;

    private SmsClient smsClient;

    @Override
    public SmsClient getObject() throws Exception {
        return this.smsClient;
    }

    @Override
    public Class<?> getObjectType() {
        return SmsClient.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        final IClientProfile clientProfile = DefaultProfile.getProfile(
                this.smsProperties.getRegion(),
                this.smsProperties.getAccessKeyId(),
                this.smsProperties.getAccessKeySecret());
        DefaultProfile.addEndpoint(
                this.smsProperties.getRegion(), this.smsProperties.getRegion(),
                this.smsProperties.getProduct(), this.smsProperties.getDomain());
        final IAcsClient acsClient = new DefaultAcsClient(clientProfile);
        this.smsClient = new SmsClient(acsClient, this.smsProperties);
    }
}
