package me.yuxiaoyao.sl651.netty.server.telemetry;

import me.yuxiaoyao.sl651.netty.server.read.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author KerryMiBook on 24/11/9
 */


public class TelemetryBaseConfigTable {


    /**
     * 表D.1 遥测站基本配置表
     */
    private static final Map<Integer, IObjectReader<?>> DATA_DEFINE_MAP = new ConcurrentHashMap<>();


    static {
        initDataDefine();
    }

    public static IObjectReader<?> getObjectReader(int flag) {
        return DATA_DEFINE_MAP.get(flag);
    }

    public static boolean isValid(int flag) {
        return DATA_DEFINE_MAP.containsKey(flag);
    }


    private static void initDataDefine() {
        // 中心站地址
        DATA_DEFINE_MAP.put(0x01, new DynamicByteItemCountNumberReader(4, 1));

        // 遥测站地址
        DATA_DEFINE_MAP.put(0x02, new DynamicByteItemCountNumberReader(0x02, 5));

        // 密码
        DATA_DEFINE_MAP.put(0x03, new DynamicHexStringReader());

        // 中心站1主信道类型及地址
        DATA_DEFINE_MAP.put(0x04, new DynamicStationChannelConfigReader());

        // 中心站1备用信道类型及地址
        DATA_DEFINE_MAP.put(0x05, new DynamicStationChannelConfigReader());

        // 中心站2主信道类型及地址
        DATA_DEFINE_MAP.put(0x06, new DynamicStationChannelConfigReader());

        // 中心站2备用信道类型及地址
        DATA_DEFINE_MAP.put(0x07, new DynamicStationChannelConfigReader());

        // 中心站3主信道类型及地址
        DATA_DEFINE_MAP.put(0x08, new DynamicStationChannelConfigReader());

        // 中心站3备用信道类型及地址
        DATA_DEFINE_MAP.put(0x09, new DynamicStationChannelConfigReader());

        // 中心站4主信道类型及地址
        DATA_DEFINE_MAP.put(0x0A, new DynamicStationChannelConfigReader());

        // 中心站4备用信道类型及地址
        DATA_DEFINE_MAP.put(0x0B, new DynamicStationChannelConfigReader());

        // 工作方式
        DATA_DEFINE_MAP.put(0x0C, new DynamicBcdIntReader());

        // 遥测站采集要素设置, 表D.2 遥测站监测要素定义表
        DATA_DEFINE_MAP.put(0x0D, new DynamicTelemetryCollectEleSettingReader());

        // TODO 0x0E

        // 遥测站通信设备识别号
        DATA_DEFINE_MAP.put(0x0F, new DynamicConDeviceSnDataDefine());

    }


}
