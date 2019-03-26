package cn.javaer.aliyun.sms;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cn-src
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

    @Test
    public void send() {
        final Map<String, String> param = new HashMap<>();
        param.put("code", "123456");
        final BatchSmsTemplate batchSmsTemplate = BatchSmsTemplate.builder()
                .phoneNumbers(Arrays.asList("", ""))
                .signNames(Arrays.asList("", ""))
                .templateCode("")
                .templateParams((Arrays.asList(param, param)))
                .build();
        this.smsClient.send(batchSmsTemplate);
    }
}