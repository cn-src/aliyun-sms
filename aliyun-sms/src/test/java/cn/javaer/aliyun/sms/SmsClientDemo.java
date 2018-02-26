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
        final SmsTemplate authenticationSmsTemplate = SmsTemplate.builder()
                .templateCode(System.getenv("aliyun.sms.authentication.signName"))
                .signName(System.getenv("aliyun.sms.authentication.templateCode")).build();
        this.smsClient.setAuthenticationSmsTemplate(authenticationSmsTemplate);
        this.smsClient.sendAuthenticationCode(System.getenv("aliyun.sms.authentication.phoneNumber"));
    }
}