package cn.javaer.aliyun.spring.boot.autoconfigure.sms;

import cn.javaer.aliyun.sms.SmsClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author cn-src
 */
@SpringBootTest(classes = SmsAutoConfiguration.class)
@RunWith(SpringRunner.class)
public class SmsAutoConfigurationDemo {

    @Autowired
    private SmsClient smsClient;

    @Test
    public void sendVerificationCode() {
        final String phoneNumber = System.getenv("aliyun.sms.authentication.phoneNumber");
        this.smsClient.sendVerificationCode("authentication", phoneNumber);
    }
}