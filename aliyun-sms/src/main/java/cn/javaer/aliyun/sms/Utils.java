package cn.javaer.aliyun.sms;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;

import java.util.Random;

/**
 * @author zhangpeng
 */
class Utils {

    private static final String SUCCESS_CODE = "OK";
    private static final Random RANDOM = new Random();

    /**
     * 生成随机数.
     *
     * @param startInclusive 随机范围起始数字（包含）
     * @param endExclusive 随机范围结束数字（不包含）
     *
     * @return 随机数
     */
    static int nextInt(final int startInclusive, final int endExclusive) {
        checkArgument(endExclusive >= startInclusive, "Start value must be smaller or equal to end value.");
        checkArgument(startInclusive >= 0, "Both range values must be non-negative.");

        if (startInclusive == endExclusive) {
            return startInclusive;
        }

        return startInclusive + RANDOM.nextInt(endExclusive - startInclusive);
    }

    static void checkSmsResponse(final SendSmsResponse response) {
        if (null == response) {
            throw new SmsException("Response is null");
        }
        if (!SUCCESS_CODE.equalsIgnoreCase(response.getCode())) {
            throw new SmsException("Response code is '" + response.getCode() + "'");
        }
    }

    static void checkNotEmpty(final String str, final String message) {
        if (null == str || str.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    static void checkArgument(final boolean expression, final String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    static <T> T tryFun(final CheckedSupplier<T> fun) {
        try {
            return fun.get();
        } catch (final Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                throw new SmsException(e);
            }
        }
    }

    static void tryFun(final CheckedVoid fun) {
        try {
            fun.call();
        } catch (final Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                throw new SmsException(e);
            }
        }
    }
}
