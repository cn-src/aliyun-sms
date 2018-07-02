package cn.javaer.aliyun.sms;

/**
 * The interface CheckedVoid.
 *
 * @author cn-src
 */
@FunctionalInterface
interface CheckedVoid {
    /**
     * Call.
     *
     * @throws Exception the exception
     */
    void call() throws Exception;
}
