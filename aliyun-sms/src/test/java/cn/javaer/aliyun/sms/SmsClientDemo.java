package cn.javaer.aliyun.sms;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

/**
 * @author zhangpeng
 */
public class SmsClientDemo {
    private SmsClient smsClient;

    @Before
    public void setUp() throws Exception {
        final String accessKeyId = System.getenv("aliyun.sms.accessKeyId");
        final String accessKeySecret = System.getenv("aliyun.sms.accessKeySecret");
        this.smsClient = new SmsClient(accessKeyId, accessKeySecret);
    }

    @Test
    public void sendAuthenticationCode() {
        final String signName = System.getenv("aliyun.sms.authentication.signName");
        final String templateCode = System.getenv("aliyun.sms.authentication.templateCode");
        final SmsTemplate smsTemplate = SmsTemplate.builder()
                .signName(signName)
                .templateCode(templateCode)
                .addTemplateParam("code", "123456")
                .phoneNumbers(Collections.singletonList(System.getenv("aliyun.sms.authentication.phoneNumber")))
                .build();

        this.smsClient.send(smsTemplate);
    }
}