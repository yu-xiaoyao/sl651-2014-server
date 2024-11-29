package me.yuxiaoyao.sl651.netty.server.util;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.Map;

/**
 * @author kerryzhang on 2024/11/18
 */

public class SerialNumbers {

    private static final Map<String, Integer> serialNumberMap;

    static {
        Cache<String, Integer> cache = Caffeine.newBuilder()
                .maximumSize(2048)
                .build();
        serialNumberMap = cache.asMap();
    }


    public static int nextSerialNumber(String deviceId) {
        return serialNumberMap.compute(deviceId, (k, v) -> {
            if (v == null) {
                return 0;
            }
            v = v + 1;
            if (v > 65535) {
                v = 0;
            }
            return v;
        });
    }

}
