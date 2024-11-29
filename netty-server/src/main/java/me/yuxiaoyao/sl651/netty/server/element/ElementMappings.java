//package me.yuxiaoyao.sl651.netty.server.element;
//
//
//import me.yuxiaoyao.sl651.netty.server.define.*;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * @author kerryzhang on 2024/11/19
// */
//
//public class ElementMappings {
//    private static final Map<Integer, IDataDefine<?>> DD_MAP = new ConcurrentHashMap<>();
//
//    static {
//        initDataDefine();
//    }
//
//    public static IDataDefine<?> getDataDefine(int flag) {
//        return DD_MAP.get(flag);
//    }
//
//    private static void initDataDefine() {
//        // 观测时间引导符
//        DD_MAP.put(0xF0, new ObservationTimeDataDefine());
//        // 测站编码引导符
//        //DD_MAP.put(0xF1, new TelemetryStationDataDefine(0xF1));
//        // 人工置数
//        //DD_MAP.put(0xF2, new ManualSetDataDefine(0xF2));
//        // 1 小时内每 5 分钟时段雨量 e（每组雨量占 1 字节 HEX，最大值 25.4 毫米，数据中不含小数点；FFH表示非法数据。）
//        DD_MAP.put(0xF4, new ByteItemCountDataDefine(12, 1));
//        // 1 小时内 5 分钟间隔相对水位
//        DD_MAP.put(0xF5, new ByteItemCountDataDefine(12, 2));
//
//        // --------------------------------
//        // 断面面积
//        DD_MAP.put(0x01, NumberDataDefine.INSTANCE_8_2);
//        // 瞬时气温
//        DD_MAP.put(0x02, NumberDataDefine.INSTANCE_3_1);
//        // 瞬时水温
//        DD_MAP.put(0x03, NumberDataDefine.INSTANCE_3_1);
//        // 时间步长码
//        DD_MAP.put(0x04, new TimeStepDataDefine());
//        // 时段长
//        DD_MAP.put(0x05, TimeHHmmDataDefine.INSTANCE);
//        // 日蒸发量
//        DD_MAP.put(0x06, NumberDataDefine.INSTANCE_5_1);
//        // 当前蒸发
//        DD_MAP.put(0x07, NumberDataDefine.INSTANCE_5_1);
//        // 气压
//        DD_MAP.put(0x08, NumberDataDefine.INSTANCE_5_0);
//        // 闸坝、水库闸门开启高度
//        DD_MAP.put(0x09, NumberDataDefine.INSTANCE_5_2);
//        // 输水设备、闸门(组)编号
//        DD_MAP.put(0x0A, NumberDataDefine.INSTANCE_3_0);
//        // 输水设备类别
//        DD_MAP.put(0x0B, new NumberDataDefine(1, 0));
//        // 水库、闸坝闸门开启孔数
//        DD_MAP.put(0x0C, NumberDataDefine.INSTANCE_3_0);
//        // 地温
//        DD_MAP.put(0x0D, NumberDataDefine.INSTANCE_3_1);
//        // 地下水瞬时埋深
//        DD_MAP.put(0x0E, NumberDataDefine.INSTANCE_6_2);
//        // 波浪高度
//        DD_MAP.put(0x0F, NumberDataDefine.INSTANCE_5_2);
//        // 10 厘米处土壤含水量
//        DD_MAP.put(0x10, NumberDataDefine.INSTANCE_4_1);
//        // 20 厘米处土壤含水量
//        DD_MAP.put(0x11, NumberDataDefine.INSTANCE_4_1);
//        // 30 厘米处土壤含水量
//        DD_MAP.put(0x12, NumberDataDefine.INSTANCE_4_1);
//        // 40 厘米处土壤含水量
//        DD_MAP.put(0x13, NumberDataDefine.INSTANCE_4_1);
//        // 50 厘米处土壤含水量
//        DD_MAP.put(0x14, NumberDataDefine.INSTANCE_4_1);
//        // 60 厘米处土壤含水量
//        DD_MAP.put(0x15, NumberDataDefine.INSTANCE_4_1);
//        // 80 厘米处土壤含水量
//        DD_MAP.put(0x16, NumberDataDefine.INSTANCE_4_1);
//        // 100 厘米处土壤含水量
//        DD_MAP.put(0x17, NumberDataDefine.INSTANCE_4_1);
//        // 湿度
//        DD_MAP.put(0x18, NumberDataDefine.INSTANCE_4_1);
//        // 开机台数
//        DD_MAP.put(0x19, NumberDataDefine.INSTANCE_2_0);
//        // 1 小时时段降水量
//        DD_MAP.put(0x1A, NumberDataDefine.INSTANCE_5_1);
//        // 2 小时时段降水量
//        DD_MAP.put(0x1B, NumberDataDefine.INSTANCE_5_1);
//        // 3 小时时段降水量
//        DD_MAP.put(0x1C, NumberDataDefine.INSTANCE_5_1);
//        // 6 小时时段降水量
//        DD_MAP.put(0x1D, NumberDataDefine.INSTANCE_5_1);
//        // 12 小时时段降水量
//        DD_MAP.put(0x1E, NumberDataDefine.INSTANCE_5_1);
//        // 日降水量
//        DD_MAP.put(0x1F, NumberDataDefine.INSTANCE_5_1);
//        // 当前降水量
//        DD_MAP.put(0x20, NumberDataDefine.INSTANCE_5_1);
//        // 1 分钟时段降水量
//        DD_MAP.put(0x21, NumberDataDefine.INSTANCE_5_1);
//        // 5 分钟时段降水量
//        DD_MAP.put(0x22, NumberDataDefine.INSTANCE_5_1);
//        // 10 分钟时段降水量
//        DD_MAP.put(0x23, NumberDataDefine.INSTANCE_5_1);
//        // 30 分钟时段降水量
//        DD_MAP.put(0x24, NumberDataDefine.INSTANCE_5_1);
//        // 暴雨量
//        DD_MAP.put(0x25, NumberDataDefine.INSTANCE_5_1);
//        // 降水量累计值
//        DD_MAP.put(0x26, NumberDataDefine.INSTANCE_6_1);
//        // 瞬时流量、抽水流量
//        DD_MAP.put(0x27, NumberDataDefine.INSTANCE_9_3);
//        // 取(排）水口流量 1
//        DD_MAP.put(0x28, NumberDataDefine.INSTANCE_9_3);
//        // 取(排）水口流量 2
//        DD_MAP.put(0x29, NumberDataDefine.INSTANCE_9_3);
//        // 取(排）水口流量 3
//        DD_MAP.put(0x2A, NumberDataDefine.INSTANCE_9_3);
//        // 取(排）水口流量 4
//        DD_MAP.put(0x2B, NumberDataDefine.INSTANCE_9_3);
//        // 取(排）水口流量 5
//        DD_MAP.put(0x2C, NumberDataDefine.INSTANCE_9_3);
//        // 取(排）水口流量 6
//        DD_MAP.put(0x2D, NumberDataDefine.INSTANCE_9_3);
//        // 取(排）水口流量 7
//        DD_MAP.put(0x2E, NumberDataDefine.INSTANCE_9_3);
//        // 取(排）水口流量 8
//        DD_MAP.put(0x2F, NumberDataDefine.INSTANCE_9_3);
//        // 总出库流量、过闸总流量
//        DD_MAP.put(0x30, NumberDataDefine.INSTANCE_9_3);
//        // 输水设备流量、过闸(组)流量
//        DD_MAP.put(0x31, NumberDataDefine.INSTANCE_9_3);
//        // 输沙量
//        DD_MAP.put(0x32, NumberDataDefine.INSTANCE_11_3);
//        // 风向
//        DD_MAP.put(0x33, NumberDataDefine.INSTANCE_2_0);
//        // 风力(级)
//        DD_MAP.put(0x34, NumberDataDefine.INSTANCE_2_0);
//        // 风速
//        DD_MAP.put(0x35, NumberDataDefine.INSTANCE_4_1);
//        // 断面平均流速
//        DD_MAP.put(0x36, NumberDataDefine.INSTANCE_5_3);
//        // 当前瞬时流速
//        DD_MAP.put(0x37, NumberDataDefine.INSTANCE_5_3);
//        // 电源电压
//        DD_MAP.put(0x38, NumberDataDefine.INSTANCE_4_2);
//        // 瞬时河道水位、潮位
//        DD_MAP.put(0x39, NumberDataDefine.INSTANCE_7_3);
//        // 库(闸、站)下水位
//        DD_MAP.put(0x3A, NumberDataDefine.INSTANCE_7_3);
//        // 库(闸、站)上水位
//        DD_MAP.put(0x3B, NumberDataDefine.INSTANCE_7_3);
//        // 取(排）水口水位 1
//        DD_MAP.put(0x3C, NumberDataDefine.INSTANCE_7_3);
//        // 取(排）水口水位 2
//        DD_MAP.put(0x3D, NumberDataDefine.INSTANCE_7_3);
//        // 取(排）水口水位 3
//        DD_MAP.put(0x3E, NumberDataDefine.INSTANCE_7_3);
//        // 取(排）水口水位 4
//        DD_MAP.put(0x3F, NumberDataDefine.INSTANCE_7_3);
//        // 取(排）水口水位 5
//        DD_MAP.put(0x40, NumberDataDefine.INSTANCE_7_3);
//        // 取(排）水口水位 6
//        DD_MAP.put(0x41, NumberDataDefine.INSTANCE_7_3);
//        // 取(排）水口水位 7
//        DD_MAP.put(0x42, NumberDataDefine.INSTANCE_7_3);
//        // 取(排）水口水位 8
//        DD_MAP.put(0x43, NumberDataDefine.INSTANCE_7_3);
//        // 含沙量
//        DD_MAP.put(0x44, NumberDataDefine.INSTANCE_9_3);
//
//        // 遥测站状态及报警信息（定义见表 58）
//        // 直接使用 QueryTelemetryStateAndAlarmFuncDecoder 解析.
//        // TELEMETRY_STATE_AND_ALARM(0x45, "ZT", new TelemetryStateAndAlarmDataDefine(), "telecomStateAndAlarm", null),
//
//        // pH 值
//        DD_MAP.put(0x46, NumberDataDefine.INSTANCE_4_2);
//        // 溶解氧
//        DD_MAP.put(0x47, NumberDataDefine.INSTANCE_4_2);
//        // 电导率
//        DD_MAP.put(0x48, NumberDataDefine.INSTANCE_5_0);
//        // 浊度
//        DD_MAP.put(0x49, NumberDataDefine.INSTANCE_3_0);
//        // 高锰酸盐指数
//        DD_MAP.put(0x4A, NumberDataDefine.INSTANCE_4_1);
//        // 氧化还原电位
//        DD_MAP.put(0x4B, NumberDataDefine.INSTANCE_5_1);
//        // 氨氮
//        DD_MAP.put(0x4C, NumberDataDefine.INSTANCE_6_2);
//        // 总磷
//        DD_MAP.put(0x4D, NumberDataDefine.INSTANCE_5_3);
//        // 总氮
//        DD_MAP.put(0x4E, NumberDataDefine.INSTANCE_5_2);
//        // 总有机碳
//        DD_MAP.put(0x4F, NumberDataDefine.INSTANCE_4_2);
//        // 铜
//        DD_MAP.put(0x50, NumberDataDefine.INSTANCE_7_4);
//        // 锌
//        DD_MAP.put(0x51, NumberDataDefine.INSTANCE_6_4);
//        // 硒
//        DD_MAP.put(0x52, NumberDataDefine.INSTANCE_7_5);
//        // 砷
//        DD_MAP.put(0x53, NumberDataDefine.INSTANCE_7_5);
//        // 总汞
//        DD_MAP.put(0x54, NumberDataDefine.INSTANCE_7_5);
//        // 镉
//        DD_MAP.put(0x55, NumberDataDefine.INSTANCE_7_5);
//        // 铅
//        DD_MAP.put(0x56, NumberDataDefine.INSTANCE_7_5);
//        // 叶绿素 a
//        DD_MAP.put(0x57, NumberDataDefine.INSTANCE_4_2);
//        // 水压 1
//        DD_MAP.put(0x58, NumberDataDefine.INSTANCE_5_2);
//        // 水压 2
//        DD_MAP.put(0x59, NumberDataDefine.INSTANCE_5_2);
//        // 水压 3
//        DD_MAP.put(0x5A, NumberDataDefine.INSTANCE_5_2);
//        // 水压 4
//        DD_MAP.put(0x5B, NumberDataDefine.INSTANCE_5_2);
//        // 水压 5
//        DD_MAP.put(0x5C, NumberDataDefine.INSTANCE_5_2);
//        // 水压 6
//        DD_MAP.put(0x5D, NumberDataDefine.INSTANCE_5_2);
//        // 水压 7
//        DD_MAP.put(0x5E, NumberDataDefine.INSTANCE_5_2);
//        // 水压 8
//        DD_MAP.put(0x5F, NumberDataDefine.INSTANCE_5_2);
//        // 水表 1 剩余水量
//        DD_MAP.put(0x60, NumberDataDefine.INSTANCE_11_3);
//        // 水表 2 剩余水量
//        DD_MAP.put(0x61, NumberDataDefine.INSTANCE_11_3);
//        // 水表 3 剩余水量
//        DD_MAP.put(0x62, NumberDataDefine.INSTANCE_11_3);
//        // 水表 4 剩余水量
//        DD_MAP.put(0x63, NumberDataDefine.INSTANCE_11_3);
//        // 水表 5 剩余水量
//        DD_MAP.put(0x64, NumberDataDefine.INSTANCE_11_3);
//        // 水表 6 剩余水量
//        DD_MAP.put(0x65, NumberDataDefine.INSTANCE_11_3);
//        // 水表 7 剩余水量
//        DD_MAP.put(0x66, NumberDataDefine.INSTANCE_11_3);
//        // 水表 8 剩余水量
//        DD_MAP.put(0x67, NumberDataDefine.INSTANCE_11_3);
//
//        // 水表 1 每小时水量
//        DD_MAP.put(0x68, NumberDataDefine.INSTANCE_10_2);
//        // 水表 2 每小时水量
//        DD_MAP.put(0x69, NumberDataDefine.INSTANCE_10_2);
//        // 水表 3 每小时水量
//        DD_MAP.put(0x6A, NumberDataDefine.INSTANCE_10_2);
//        // 水表 4 每小时水量
//        DD_MAP.put(0x6B, NumberDataDefine.INSTANCE_10_2);
//        // 水表 5 每小时水量
//        DD_MAP.put(0x6C, NumberDataDefine.INSTANCE_10_2);
//        // 水表 6 每小时水量
//        DD_MAP.put(0x6D, NumberDataDefine.INSTANCE_10_2);
//        // 水表 7 每小时水量
//        DD_MAP.put(0x6E, NumberDataDefine.INSTANCE_10_2);
//        // 水表 8 每小时水量
//        DD_MAP.put(0x6F, NumberDataDefine.INSTANCE_10_2);
//
//        // 交流 A 相电压
//        DD_MAP.put(0x70, NumberDataDefine.INSTANCE_4_1);
//        // 交流 B 相电压
//        DD_MAP.put(0x71, NumberDataDefine.INSTANCE_4_1);
//        // 交流 C 相电压
//        DD_MAP.put(0x72, NumberDataDefine.INSTANCE_4_1);
//        // 交流 A 相电流
//        DD_MAP.put(0x73, NumberDataDefine.INSTANCE_4_1);
//        // 交流 B 相电流
//        DD_MAP.put(0x74, NumberDataDefine.INSTANCE_4_1);
//        // 交流 C 相电流
//        DD_MAP.put(0x75, NumberDataDefine.INSTANCE_4_1);
//
//    }
//}
