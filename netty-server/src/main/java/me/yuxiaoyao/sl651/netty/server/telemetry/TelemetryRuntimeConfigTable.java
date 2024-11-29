package me.yuxiaoyao.sl651.netty.server.telemetry;

import me.yuxiaoyao.sl651.netty.server.read.IObjectReader;
import me.yuxiaoyao.sl651.netty.server.read.DynamicNumberReader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kerryzhang on 2024/11/09
 */

public class TelemetryRuntimeConfigTable {


    /**
     * 表D.4 遥测站运行参数配置表
     */
    private static final Map<Integer, IObjectReader<?>> DATA_DEFINE_MAP = new ConcurrentHashMap<>();


    public static IObjectReader<?> getObjectReader(int flag) {
        return DATA_DEFINE_MAP.get(flag);
    }


    static {
        initDataDefine();
    }


    private static void initDataDefine() {
        // 定时报时间间隔
        DATA_DEFINE_MAP.put(0x20, DynamicNumberReader.INSTANCE_2_0);
        // 加报时间间隔
        DATA_DEFINE_MAP.put(0x21, DynamicNumberReader.INSTANCE_2_0);
        // 降水量日起始时间
        DATA_DEFINE_MAP.put(0x22, DynamicNumberReader.INSTANCE_2_0);
        // 采样间隔
        DATA_DEFINE_MAP.put(0x23, DynamicNumberReader.INSTANCE_4_0);
        // 水位数据存储间隔
        DATA_DEFINE_MAP.put(0x24, DynamicNumberReader.INSTANCE_2_0);
        // 雨量计分辨力
        DATA_DEFINE_MAP.put(0x25, DynamicNumberReader.INSTANCE_2_1);
        // 水位计分辨力
        DATA_DEFINE_MAP.put(0x26, DynamicNumberReader.INSTANCE_2_1);
        // 雨量加报阈值
        DATA_DEFINE_MAP.put(0x27, DynamicNumberReader.INSTANCE_2_0);
        // 水位基值1
        DATA_DEFINE_MAP.put(0x28, DynamicNumberReader.INSTANCE_7_3);
        // 水位基值2
        DATA_DEFINE_MAP.put(0x29, DynamicNumberReader.INSTANCE_7_3);
        // 水位基值3
        DATA_DEFINE_MAP.put(0x2A, DynamicNumberReader.INSTANCE_7_3);
        // 水位基值4
        DATA_DEFINE_MAP.put(0x2B, DynamicNumberReader.INSTANCE_7_3);
        // 水位基值5
        DATA_DEFINE_MAP.put(0x2C, DynamicNumberReader.INSTANCE_7_3);
        // 水位基值6
        DATA_DEFINE_MAP.put(0x2D, DynamicNumberReader.INSTANCE_7_3);
        // 水位基值7
        DATA_DEFINE_MAP.put(0x2E, DynamicNumberReader.INSTANCE_7_3);
        // 水位基值8
        DATA_DEFINE_MAP.put(0x2F, DynamicNumberReader.INSTANCE_7_3);
        // 水位修正基值 1
        DATA_DEFINE_MAP.put(0x30, DynamicNumberReader.INSTANCE_5_3);
        // 水位修正基值 2
        DATA_DEFINE_MAP.put(0x31, DynamicNumberReader.INSTANCE_5_3);
        // 水位修正基值 3
        DATA_DEFINE_MAP.put(0x32, DynamicNumberReader.INSTANCE_5_3);
        // 水位修正基值 4
        DATA_DEFINE_MAP.put(0x33, DynamicNumberReader.INSTANCE_5_3);
        // 水位修正基值 5
        DATA_DEFINE_MAP.put(0x34, DynamicNumberReader.INSTANCE_5_3);
        // 水位修正基值 6
        DATA_DEFINE_MAP.put(0x35, DynamicNumberReader.INSTANCE_5_3);
        // 水位修正基值 7
        DATA_DEFINE_MAP.put(0x36, DynamicNumberReader.INSTANCE_5_3);
        // 水位修正基值 8
        DATA_DEFINE_MAP.put(0x37, DynamicNumberReader.INSTANCE_5_3);
        // 加报水位 1
        DATA_DEFINE_MAP.put(0x38, DynamicNumberReader.INSTANCE_4_2);
        // 加报水位 2
        DATA_DEFINE_MAP.put(0x39, DynamicNumberReader.INSTANCE_4_2);
        // 加报水位 3
        DATA_DEFINE_MAP.put(0x3A, DynamicNumberReader.INSTANCE_4_2);
        // 加报水位 4
        DATA_DEFINE_MAP.put(0x3B, DynamicNumberReader.INSTANCE_4_2);
        // 加报水位 5
        DATA_DEFINE_MAP.put(0x3C, DynamicNumberReader.INSTANCE_4_2);
        // 加报水位 6
        DATA_DEFINE_MAP.put(0x3D, DynamicNumberReader.INSTANCE_4_2);
        // 加报水位 7
        DATA_DEFINE_MAP.put(0x3E, DynamicNumberReader.INSTANCE_4_2);
        // 加报水位 8
        DATA_DEFINE_MAP.put(0x3F, DynamicNumberReader.INSTANCE_4_2);
        // 加报水位以上加报阈值
        DATA_DEFINE_MAP.put(0x40, DynamicNumberReader.INSTANCE_3_2);
        // 加报水位以下加报阈值
        DATA_DEFINE_MAP.put(0x41, DynamicNumberReader.INSTANCE_3_2);
        // 流量加报阈值
        DATA_DEFINE_MAP.put(0x42, DynamicNumberReader.INSTANCE_5_3);
        // 流速加报阈值
        DATA_DEFINE_MAP.put(0x43, DynamicNumberReader.INSTANCE_6_3);
        // 闸位加报阈值
        DATA_DEFINE_MAP.put(0x44, DynamicNumberReader.INSTANCE_3_2);
        // 功率加报阈值
        DATA_DEFINE_MAP.put(0x45, DynamicNumberReader.INSTANCE_6_0);
        // 气压加报阈值
        DATA_DEFINE_MAP.put(0x46, DynamicNumberReader.INSTANCE_4_0);
        // 风速加报阈值
        DATA_DEFINE_MAP.put(0x47, DynamicNumberReader.INSTANCE_4_2);
        // 水温加报阈值
        DATA_DEFINE_MAP.put(0x48, DynamicNumberReader.INSTANCE_2_1);

    }


}
