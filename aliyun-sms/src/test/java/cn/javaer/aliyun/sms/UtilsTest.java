package cn.javaer.aliyun.sms;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author cn-src
 */
public class UtilsTest {

    @Test
    public void toJsonStr() {
        final Map<String, String> map = new HashMap<>();
        map.put("k1", "v1");
        map.put("k2", "v2");
        map.put("k3", "v3");
        map.put("k\"", "v\"");
        assertEquals("{\"k1\":\"v1\",\"k2\":\"v2\",\"k\\\"\":\"v\\\"\",\"k3\":\"v3\"}", Utils.toJsonStr(map));
    }
}