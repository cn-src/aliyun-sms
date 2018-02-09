package cn.javaer.aliyun.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
            this.templateParam.put(key, value);
            return this;
        }
    }
}
