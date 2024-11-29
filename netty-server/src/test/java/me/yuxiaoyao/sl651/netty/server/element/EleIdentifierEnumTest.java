package me.yuxiaoyao.sl651.netty.server.element;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kerryzhang on 2024/11/07
 */

public class EleIdentifierEnumTest {

    @Test
    public void test_repeat_hex_code() {
        EleIdentifierEnum[] values = EleIdentifierEnum.values();

        int count = 0;
        Map<Integer, EleIdentifierEnum> map = new HashMap<>();
        for (EleIdentifierEnum value : values) {
            var flag = value.getFlag();
            EleIdentifierEnum put = map.put(flag, value);
            if (put != null) {
                count++;
                System.out.println("重复定义. hexFlag = " + Integer.toHexString(flag) + ", 已有 = " + value + ", 新 = " + put);
            }
        }
        System.out.println("重复数: " + count);

    }



    @Test
    public void test_repeat_flag_id() {
        EleIdentifierEnum[] values = EleIdentifierEnum.values();

        int count = 0;
        Map<String, EleIdentifierEnum> map = new HashMap<>();
        for (EleIdentifierEnum value : values) {
            var flag = value.getEleId();
            EleIdentifierEnum put = map.put(flag, value);
            if (put != null) {
                count++;
                System.out.println("重复定义. eleId = " + flag + ", 已有 = " + value + ", 新 = " + put);
            }
        }
        System.out.println("重复数: " + count);

    }

}