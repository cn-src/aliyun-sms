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

import cn.javaer.aliyun.sms.SmsTemplate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author zhangpeng
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "aliyun.sms")
public class SmsProperties implements InitializingBean {

    private String accessKeyId;
    private String accessKeySecret;
    private String signName;
    private Map<String, SmsTemplate> templates;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (null != this.templates) {
            for (final SmsTemplate smsTemplate : this.templates.values()) {
                if (null == smsTemplate.getSignName()) {
                    smsTemplate.setSignName(this.signName);
                }
            }
        }
    }
}
