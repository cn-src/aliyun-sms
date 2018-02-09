package cn.javaer.aliyun.sms;

import java.util.Random;

/**
 * @author zhangpeng
 */
class Utils {

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

        if (endExclusive < startInclusive) {
            throw new IllegalArgumentException("Start value must be smaller or equal to end value.");
        }
        if (startInclusive < 0) {
            throw new IllegalArgumentException("Both range values must be non-negative.");
        }

        if (startInclusive == endExclusive) {
            return startInclusive;
        }

        return startInclusive + RANDOM.nextInt(endExclusive - startInclusive);
    }

}
