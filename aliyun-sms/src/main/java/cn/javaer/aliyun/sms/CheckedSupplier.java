package cn.javaer.aliyun.sms;

/**
 * @author zhangpeng
 */
interface CheckedSupplier<T> {
    T get() throws Exception;
}
