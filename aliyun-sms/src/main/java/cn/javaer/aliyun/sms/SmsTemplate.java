package cn.javaer.aliyun.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangpeng
 */
@Builder(builderClassName = "Builder", toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SmsTemplate {
    private String signName;
    private String templateCode;
    private Map<String, String> templateParam;
    private List<String> phoneNumbers;

    public static class Builder {
        /**
         * 添加短信模板参数.
         *
         * @param key the key
         * @param value the value
         *
         * @return this
         */
        public Builder addTemplateParam(final String key, final String value) {
            if (null == this.templateParam) {
                this.templateParam = new HashMap<>(3);
            }
            this.templateParam.put(key, value);
            return this;
        }
    }
}
