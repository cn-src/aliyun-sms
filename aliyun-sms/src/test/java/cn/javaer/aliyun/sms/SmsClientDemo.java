package cn.javaer.aliyun.sms;

import org.junit.Before;
import org.junit.Test;

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
        final SmsTemplate authenticationSmsTemplate = SmsTemplate.builder()
                .templateCode(templateCode)
                .signName(signName).build();
        this.smsClient.setAuthenticationSmsTemplate(authenticationSmsTemplate);
        this.smsClient.sendAuthenticationCode(System.getenv("aliyun.sms.authentication.phoneNumber"));
    }
}