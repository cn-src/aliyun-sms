package cn.javaer.aliyun.sms;

import com.aliyuncs.CommonResponse;
import com.google.gson.Gson;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 工具类，内部使用，作为库自身尽可能减少对第三方库的依赖.
 *
 * @author cn-src
 */
class Utils {

    private static final String SUCCESS_CODE = "OK";
    private static final String PHONE_NUMBER_REGEX = "1\\d{10}";

    /**
     * 生成随机验证码.
     *
     * @return 随机数
     */
    static int randomCode() {
        return 100_000 + ThreadLocalRandom.current().nextInt(1_000_000 - 100_000);
    }

    /**
     * Map 转 json 字符串的简单实现.
     *
     * @param map the map
     *
     * @return the json string
     */
    static String toJsonStr(final Map<String, String> map) {
        if (null == map || map.isEmpty()) {
            return null;
        }

        final StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (final Map.Entry<String, String> entry : map.entrySet()) {
            sb.append('"')
                    .append(entry.getKey().replace("\"", "\\\""))
                    .append('"')
                    .append(':')
                    .append('"')
                    .append(entry.getValue().replace("\"", "\\\""))
                    .append('"')
                    .append(',');
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append('}');
        return sb.toString();
    }

    /**
     * 校验 SmsTemplate.
     *
     * @param template the SmsTemplate
     */
    static void checkSmsTemplate(final SmsTemplate template) {
        if (null == template.getSignName() || template.getSignName().isEmpty()) {
            throw new IllegalArgumentException("SmsTemplate signName must be not empty");
        }
        if (null == template.getTemplateCode() || template.getTemplateCode().isEmpty()) {
            throw new IllegalArgumentException("SmsTemplate templateCode must be not empty");

        }
        if (null == template.getPhoneNumbers() || template.getPhoneNumbers().isEmpty()) {
            throw new IllegalArgumentException("SmsTemplate phoneNumbers must be not empty");
        }
    }

    /**
     * 校验 SendSmsResponse 状态.
     *
     * @param response the SendSmsResponse
     */
    static void checkSmsResponse(final CommonResponse response) {
        if (null == response) {
            throw new SmsException("Response is null");
        }
        final Gson gson = new Gson();
        final Map<String, String> json = gson.fromJson(response.getData(), Map.class);
        if (!SUCCESS_CODE.equalsIgnoreCase(json.get("Code"))) {
            throw new SmsException("Response is '" + response.getData() + "'");
        }
    }

    /**
     * 校验手机号码（中国）.
     *
     * @param phoneNumber the phone number
     */
    static void checkPhoneNumber(final String phoneNumber) {
        if (null == phoneNumber || !phoneNumber.matches(PHONE_NUMBER_REGEX)) {
            throw new IllegalArgumentException("Invalid phone number");
        }
    }

    /**
     * 校验字符串不为空.
     *
     * @param str the str
     * @param message the message
     */
    static void checkNotEmpty(final String str, final String message) {
        if (null == str || str.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }
}
