package cn.javaer.aliyun.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * 阿里云 SMS 短信模板.
 *
 * @author cn-src
 */
@Builder(builderClassName = "Builder", toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BatchSmsTemplate {
    private List<String> signNames;
    private String templateCode;
    private List<Map<String, String>> templateParams;
    private List<String> phoneNumbers;
}
