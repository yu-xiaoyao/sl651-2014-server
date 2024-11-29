//package me.yuxiaoyao.sl651.netty.server.element;
//
//
//import lombok.Getter;
//import me.yuxiaoyao.sl651.netty.server.define.*;
//
///**
// * @author kerryzhang on 2024/11/07
// */
//
//
//@Getter
//public enum EleIdentifierEnum {
//
//    // 观测时间引导符
//    OBSERVATION_TIME(0xF0, new ObservationTimeDataDefine(), "observationTime", null),
//
//    // 测站编码引导符
//    // TELEMETRY_STATION(0xF1, "ST", new TelemetryStationDataDefine(0xF1), "telemetryStation", null),
//
//    // 人工置数
//    // MANUAL_SET(0xF2, "", new ManualSetDataDefine(0xF2), "manualSet", null),
//
//
//    // 1 小时内每 5 分钟时段雨量 e（每组雨量占 1 字节 HEX，最大值 25.4 毫米，数据中不含小数点；FFH表示非法数据。）
//    EVERY_5_MINUTES_HOUR_HYETAL(0xF4, new ByteItemCountDataDefine(12, 1), "every5MinutesHourHyetal", "0.1毫米"),
//    // 1 小时内 5 分钟间隔相对水位
//    EVERY_5_MINUTES_HOUR_WATER_LEVEL(0xF5, new ByteItemCountDataDefine(12, 2), "every5MinutesHourWaterLevel", "0.01米"),
//
//
//    // 断面面积
//    SECTION_AREA(0x01, NumberDataDefine.INSTANCE_8_2, "sectionArea", "平方米"),
//    // 瞬时气温
//    INSTANT_TEMPERATURE(0x02, NumberDataDefine.INSTANCE_3_1, "instantTemperature", "摄氏度"),
//    // 瞬时水温
//    INSTANT_WATER_TEMPERATURE(0x03, NumberDataDefine.INSTANCE_3_1, "instantWaterTemperature", "摄氏度"),
//
//    // 时间步长码
//    TIME_STEP_CODE(0x04, new TimeStepDataDefine(), "timeStepCode", null),
//
//    // 时段长
//    TIME_SEGMENT_LENGTH(0x05, TimeHHmmDataDefine.INSTANCE, "timeSegmentLength", "小时.分钟"),
//
//    // 日蒸发量
//    DAY_EMISSION(0x06, NumberDataDefine.INSTANCE_5_1, "dayEmission", "毫米"),
//    // 当前蒸发
//    CURRENT_EMISSION(0x07, NumberDataDefine.INSTANCE_5_1, "currentEmission", "毫米"),
//    // 气压
//    PRESSURE(0x08, NumberDataDefine.INSTANCE_5_0, "pressure", "百帕"),
//    // 闸坝、水库闸门开启高度
//    GATE_OPEN_HEIGHT(0x09, NumberDataDefine.INSTANCE_5_2, "gateOpenHeight", "米"),
//    // 输水设备、闸门(组)编号
//    GATE_NUMBER(0x0A, NumberDataDefine.INSTANCE_3_0, "gateNumber", null),
//    // 输水设备类别
//    GATE_TYPE(0x0B, new NumberDataDefine(1, 0), "gateType", null),
//    // 水库、闸坝闸门开启孔数
//    GATE_OPEN_NUMBER(0x0C, NumberDataDefine.INSTANCE_3_0, "gateOpenNumber", "孔"),
//    // 地温
//    GROUND_TEMPERATURE(0x0D, NumberDataDefine.INSTANCE_3_1, "groundTemperature", "摄氏度"),
//    // 地下水瞬时埋深
//    GROUND_INSTANT_DEPTH(0x0E, NumberDataDefine.INSTANCE_6_2, "groundInstantDepth", "米"),
//    // 波浪高度
//    WAVE_HEIGHT(0x0F, NumberDataDefine.INSTANCE_5_2, "waveHeight", "米"),
//    // 10 厘米处土壤含水量
//    SOIL_WATER_CONTENT_10CM(0x10, NumberDataDefine.INSTANCE_4_1, "soilWaterContent10Cm", "百分比"),
//    // 20 厘米处土壤含水量
//    SOIL_WATER_CONTENT_20CM(0x11, NumberDataDefine.INSTANCE_4_1, "soilWaterContent20Cm", "百分比"),
//    // 30 厘米处土壤含水量
//    SOIL_WATER_CONTENT_30CM(0x12, NumberDataDefine.INSTANCE_4_1, "soilWaterContent30Cm", "百分比"),
//    // 40 厘米处土壤含水量
//    SOIL_WATER_CONTENT_40CM(0x13, NumberDataDefine.INSTANCE_4_1, "soilWaterContent40Cm", "百分比"),
//    // 50 厘米处土壤含水量
//    SOIL_WATER_CONTENT_50CM(0x14, NumberDataDefine.INSTANCE_4_1, "soilWaterContent50Cm", "百分比"),
//    // 60 厘米处土壤含水量
//    SOIL_WATER_CONTENT_60CM(0x15, NumberDataDefine.INSTANCE_4_1, "soilWaterContent60Cm", "百分比"),
//    // 80 厘米处土壤含水量
//    SOIL_WATER_CONTENT_80CM(0x16, NumberDataDefine.INSTANCE_4_1, "soilWaterContent80Cm", "百分比"),
//    // 100 厘米处土壤含水量
//    SOIL_WATER_CONTENT_100CM(0x17, NumberDataDefine.INSTANCE_4_1, "soilWaterContent100Cm", "百分比"),
//    // 湿度
//    HUMIDITY(0x18, NumberDataDefine.INSTANCE_4_1, "humidity", "百分比"),
//    // 开机台数
//    BOOT_NUMBER(0x19, NumberDataDefine.INSTANCE_2_0, "bootNumber", null),
//    // 1 小时时段降水量
//    HOURLY_PRECIPITATION_1H(0x1A, NumberDataDefine.INSTANCE_5_1, "hourlyPrecipitation1H", "毫米"),
//    // 2 小时时段降水量
//    HOURLY_PRECIPITATION_2H(0x1B, NumberDataDefine.INSTANCE_5_1, "hourlyPrecipitation2H", "毫米"),
//    // 3 小时时段降水量
//    HOURLY_PRECIPITATION_3H(0x1C, NumberDataDefine.INSTANCE_5_1, "hourlyPrecipitation3H", "毫米"),
//    // 6 小时时段降水量
//    HOURLY_PRECIPITATION_6H(0x1D, NumberDataDefine.INSTANCE_5_1, "hourlyPrecipitation6H", "毫米"),
//    // 12 小时时段降水量
//    HOURLY_PRECIPITATION_12H(0x1E, NumberDataDefine.INSTANCE_5_1, "hourlyPrecipitation12H", "毫米"),
//    // 日降水量
//    DAILY_PRECIPITATION(0x1F, NumberDataDefine.INSTANCE_5_1, "dailyPrecipitation", "毫米"),
//    // 当前降水量
//    CURRENT_PRECIPITATION(0x20, NumberDataDefine.INSTANCE_5_1, "currentPrecipitation", "毫米"),
//    // 1 分钟时段降水量
//    MINUTE_PRECIPITATION_1M(0x21, NumberDataDefine.INSTANCE_5_1, "minutePrecipitation1M", "毫米"),
//    // 5 分钟时段降水量
//    MINUTE_PRECIPITATION_5M(0x22, NumberDataDefine.INSTANCE_5_1, "minutePrecipitation5M", "毫米"),
//    // 10 分钟时段降水量
//    MINUTE_PRECIPITATION_10M(0x23, NumberDataDefine.INSTANCE_5_1, "minutePrecipitation10M", "毫米"),
//    // 30 分钟时段降水量
//    MINUTE_PRECIPITATION_30M(0x24, NumberDataDefine.INSTANCE_5_1, "minutePrecipitation30M", "毫米"),
//    // 暴雨量
//    RAINY_VOLUME(0x25, NumberDataDefine.INSTANCE_5_1, "rainyVolume", "毫米"),
//    // 降水量累计值
//    PRECIPITATION_ACCUMULATION(0x26, NumberDataDefine.INSTANCE_6_1, "precipitationAccumulation", "毫米"),
//    // 瞬时流量、抽水流量
//    INSTANT_FLOW(0x27, NumberDataDefine.INSTANCE_9_3, "instantFlow", "立方米/秒"),
//    // 取(排）水口流量 1
//    GAP_FLOW_1(0x28, NumberDataDefine.INSTANCE_9_3, "gapFlow1", "立方米/秒"),
//    // 取(排）水口流量 2
//    GAP_FLOW_2(0x29, NumberDataDefine.INSTANCE_9_3, "gapFlow2", "立方米/秒"),
//    // 取(排）水口流量 3
//    GAP_FLOW_3(0x2A, NumberDataDefine.INSTANCE_9_3, "gapFlow3", "立方米/秒"),
//    // 取(排）水口流量 4
//    GAP_FLOW_4(0x2B, NumberDataDefine.INSTANCE_9_3, "gapFlow4", "立方米/秒"),
//    // 取(排）水口流量 5
//    GAP_FLOW_5(0x2C, NumberDataDefine.INSTANCE_9_3, "gapFlow5", "立方米/秒"),
//    // 取(排）水口流量 6
//    GAP_FLOW_6(0x2D, NumberDataDefine.INSTANCE_9_3, "gapFlow6", "立方米/秒"),
//    // 取(排）水口流量 7
//    GAP_FLOW_7(0x2E, NumberDataDefine.INSTANCE_9_3, "gapFlow7", "立方米/秒"),
//    // 取(排）水口流量 8
//    GAP_FLOW_8(0x2F, NumberDataDefine.INSTANCE_9_3, "gapFlow8", "立方米/秒"),
//    // 总出库流量、过闸总流量
//    TOTAL_FLOW(0x30, NumberDataDefine.INSTANCE_9_3, "totalFlow", "立方米/秒"),
//    // 输水设备流量、过闸(组)流量
//    DEVICE_FLOW(0x31, NumberDataDefine.INSTANCE_9_3, "deviceFlow", "立方米/秒"),
//    // 输沙量
//    SURFACE_WATER_VOLUME(0x32, NumberDataDefine.INSTANCE_11_3, "surfaceWaterVolume", "万吨"),
//    // 风向
//    WIND_DIRECTION(0x33, NumberDataDefine.INSTANCE_2_0, "windDirection", null),
//    // 风力(级)
//    WIND_POWER(0x34, NumberDataDefine.INSTANCE_2_0, "windPower", null),
//    // 风速
//    WIND_SPEED(0x35, NumberDataDefine.INSTANCE_4_1, "windSpeed", "米/秒"),
//    // 断面平均流速
//    AVERAGE_FLOW_SPEED(0x36, NumberDataDefine.INSTANCE_5_3, "averageFlowSpeed", "米/秒"),
//    // 当前瞬时流速
//    INSTANT_FLOW_SPEED(0x37, NumberDataDefine.INSTANCE_5_3, "instantFlowSpeed", "米/秒"),
//    // 电源电压
//    VOLTAGE(0x38, NumberDataDefine.INSTANCE_4_2, "voltage", "伏特"),
//    // 瞬时河道水位、潮位
//    INSTANT_WATER_LEVEL(0x39, NumberDataDefine.INSTANCE_7_3, "instantWaterLevel", "米"),
//    // 库(闸、站)下水位
//    LOWER_WATER_LEVEL(0x3A, NumberDataDefine.INSTANCE_7_3, "lowerWaterLevel", "米"),
//    // 库(闸、站)上水位
//    UPPER_WATER_LEVEL(0x3B, NumberDataDefine.INSTANCE_7_3, "upperWaterLevel", "米"),
//    // 取(排）水口水位 1
//    GAP_WATER_LEVEL_1(0x3C, NumberDataDefine.INSTANCE_7_3, "gapWaterLevel1", "米"),
//    // 取(排）水口水位 2
//    GAP_WATER_LEVEL_2(0x3D, NumberDataDefine.INSTANCE_7_3, "gapWaterLevel2", "米"),
//    // 取(排）水口水位 3
//    GAP_WATER_LEVEL_3(0x3E, NumberDataDefine.INSTANCE_7_3, "gapWaterLevel3", "米"),
//    // 取(排）水口水位 4
//    GAP_WATER_LEVEL_4(0x3F, NumberDataDefine.INSTANCE_7_3, "gapWaterLevel4", "米"),
//    // 取(排）水口水位 5
//    GAP_WATER_LEVEL_5(0x40, NumberDataDefine.INSTANCE_7_3, "gapWaterLevel5", "米"),
//    // 取(排）水口水位 6
//    GAP_WATER_LEVEL_6(0x41, NumberDataDefine.INSTANCE_7_3, "gapWaterLevel6", "米"),
//    // 取(排）水口水位 7
//    GAP_WATER_LEVEL_7(0x42, NumberDataDefine.INSTANCE_7_3, "gapWaterLevel7", "米"),
//    // 取(排）水口水位 8
//    GAP_WATER_LEVEL_8(0x43, NumberDataDefine.INSTANCE_7_3, "gapWaterLevel8", "米"),
//    // 含沙量
//    SURFACE_SOLID_VOLUME(0x44, NumberDataDefine.INSTANCE_9_3, "surfaceSolidVolume", "千克/立方米"),
//
//
//    // 遥测站状态及报警信息（定义见表 58）
//    // 直接使用 QueryTelemetryStateAndAlarmFuncDecoder 解析.
//    // TELEMETRY_STATE_AND_ALARM(0x45, "ZT", new TelemetryStateAndAlarmDataDefine(), "telecomStateAndAlarm", null),
//
//
//    // pH 值
//    PH_VALUE(0x46, NumberDataDefine.INSTANCE_4_2, "phValue", null),
//    // 溶解氧
//    SOLUBLE_OXYGEN(0x47, NumberDataDefine.INSTANCE_4_2, "solubleOxygen", "毫克/升"),
//    // 电导率
//    ELECTRIC_CONDUCTIVITY(0x48, NumberDataDefine.INSTANCE_5_0, "electricConductivity", "微西门/厘米"),
//    // 浊度
//    TURBIDITY(0x49, NumberDataDefine.INSTANCE_3_0, "turbidity", "度"),
//    // 高锰酸盐指数
//    PERMANGANATE_EXPONENT(0x4A, NumberDataDefine.INSTANCE_4_1, "permanganateExponent", "毫克/升"),
//    // 氧化还原电位
//    REDOX_POTENTIAL(0x4B, NumberDataDefine.INSTANCE_5_1, "redoxPotential", "毫伏"),
//    // 氨氮
//    AMMONIA_NITROGEN(0x4C, NumberDataDefine.INSTANCE_6_2, "ammoniaNitrogen", "毫克/升"),
//    // 总磷
//    TOTAL_PHOSPHORUS(0x4D, NumberDataDefine.INSTANCE_5_3, "totalPhosphorus", "毫克/升"),
//    // 总氮
//    TOTAL_NITROGEN(0x4E, NumberDataDefine.INSTANCE_5_2, "totalNitrogen", "毫克/升"),
//    // 总有机碳
//    TOTAL_ORGANIC_CARBON(0x4F, NumberDataDefine.INSTANCE_4_2, "totalOrganicCarbon", "毫克/升"),
//    // 铜
//    COPPER(0x50, NumberDataDefine.INSTANCE_7_4, "copper", "毫克/升"),
//    // 锌
//    ZINC(0x51, NumberDataDefine.INSTANCE_6_4, "zinc", "毫克/升"),
//    // 硒
//    SELENIUM(0x52, NumberDataDefine.INSTANCE_7_5, "selenium", "毫克/升"),
//    // 砷
//    ARSENIC(0x53, NumberDataDefine.INSTANCE_7_5, "arsenic", "毫克/升"),
//    // 总汞
//    TOTAL_MERCURY(0x54, NumberDataDefine.INSTANCE_7_5, "totalMercury", "毫克/升"),
//    // 镉
//    CADMIUM(0x55, NumberDataDefine.INSTANCE_7_5, "cadmium", "毫克/升"),
//    // 铅
//    LEAD(0x56, NumberDataDefine.INSTANCE_7_5, "lead", "毫克/升"),
//    // 叶绿素 a
//    CHL_A(0x57, NumberDataDefine.INSTANCE_4_2, "chlA", "毫克/升"),
//    // 水压 1
//    WATER_PRESSURE_1(0x58, NumberDataDefine.INSTANCE_5_2, "waterPressure1", "千帕"),
//    // 水压 2
//    WATER_PRESSURE_2(0x59, NumberDataDefine.INSTANCE_5_2, "waterPressure2", "千帕"),
//    // 水压 3
//    WATER_PRESSURE_3(0x5A, NumberDataDefine.INSTANCE_5_2, "waterPressure3", "千帕"),
//    // 水压 4
//    WATER_PRESSURE_4(0x5B, NumberDataDefine.INSTANCE_5_2, "waterPressure4", "千帕"),
//    // 水压 5
//    WATER_PRESSURE_5(0x5C, NumberDataDefine.INSTANCE_5_2, "waterPressure5", "千帕"),
//    // 水压 6
//    WATER_PRESSURE_6(0x5D, NumberDataDefine.INSTANCE_5_2, "waterPressure6", "千帕"),
//    // 水压 7
//    WATER_PRESSURE_7(0x5E, NumberDataDefine.INSTANCE_5_2, "waterPressure7", "千帕"),
//    // 水压 8
//    WATER_PRESSURE_8(0x5F, NumberDataDefine.INSTANCE_5_2, "waterPressure8", "千帕"),
//
//    // 水表 1 剩余水量
//    WATER_METER_1_REMAINING_WATER(0x60, NumberDataDefine.INSTANCE_11_3, "waterMeter1RemainingWater", "立方米"),
//    // 水表 2 剩余水量
//    WATER_METER_2_REMAINING_WATER(0x61, NumberDataDefine.INSTANCE_11_3, "waterMeter2RemainingWater", "立方米"),
//    // 水表 3 剩余水量
//    WATER_METER_3_REMAINING_WATER(0x62, NumberDataDefine.INSTANCE_11_3, "waterMeter3RemainingWater", "立方米"),
//    // 水表 4 剩余水量
//    WATER_METER_4_REMAINING_WATER(0x63, NumberDataDefine.INSTANCE_11_3, "waterMeter4RemainingWater", "立方米"),
//    // 水表 5 剩余水量
//    WATER_METER_5_REMAINING_WATER(0x64, NumberDataDefine.INSTANCE_11_3, "waterMeter5RemainingWater", "立方米"),
//    // 水表 6 剩余水量
//    WATER_METER_6_REMAINING_WATER(0x65, NumberDataDefine.INSTANCE_11_3, "waterMeter6RemainingWater", "立方米"),
//    // 水表 7 剩余水量
//    WATER_METER_7_REMAINING_WATER(0x66, NumberDataDefine.INSTANCE_11_3, "waterMeter7RemainingWater", "立方米"),
//    // 水表 8 剩余水量
//    WATER_METER_8_REMAINING_WATER(0x67, NumberDataDefine.INSTANCE_11_3, "waterMeter8RemainingWater", "立方米"),
//
//    // 水表 1 每小时水量
//    WATER_METER_1_PER_HOUR_WATER(0x68, NumberDataDefine.INSTANCE_10_2, "waterMeter1PerHourWater", "立方米/小时"),
//    // 水表 2 每小时水量
//    WATER_METER_2_PER_HOUR_WATER(0x69, NumberDataDefine.INSTANCE_10_2, "waterMeter2PerHourWater", "立方米/小时"),
//    // 水表 3 每小时水量
//    WATER_METER_3_PER_HOUR_WATER(0x6A, NumberDataDefine.INSTANCE_10_2, "waterMeter3PerHourWater", "立方米/小时"),
//    // 水表 4 每小时水量
//    WATER_METER_4_PER_HOUR_WATER(0x6B, NumberDataDefine.INSTANCE_10_2, "waterMeter4PerHourWater", "立方米/小时"),
//    // 水表 5 每小时水量
//    WATER_METER_5_PER_HOUR_WATER(0x6C, NumberDataDefine.INSTANCE_10_2, "waterMeter5PerHourWater", "立方米/小时"),
//    // 水表 6 每小时水量
//    WATER_METER_6_PER_HOUR_WATER(0x6D, NumberDataDefine.INSTANCE_10_2, "waterMeter6PerHourWater", "立方米/小时"),
//    // 水表 7 每小时水量
//    WATER_METER_7_PER_HOUR_WATER(0x6E, NumberDataDefine.INSTANCE_10_2, "waterMeter7PerHourWater", "立方米/小时"),
//    // 水表 8 每小时水量
//    WATER_METER_8_PER_HOUR_WATER(0x6F, NumberDataDefine.INSTANCE_10_2, "waterMeter8PerHourWater", "立方米/小时"),
//
//    // 交流 A 相电压
//    AC_VOLTAGE_A(0x70, NumberDataDefine.INSTANCE_4_1, "acVoltageA", "伏特"),
//    // 交流 B 相电压
//    AC_VOLTAGE_B(0x71, NumberDataDefine.INSTANCE_4_1, "acVoltageB", "伏特"),
//    // 交流 C 相电压
//    AC_VOLTAGE_C(0x72, NumberDataDefine.INSTANCE_4_1, "acVoltageC", "伏特"),
//    // 交流 A 相电流
//    AC_CURRENT_A(0x73, NumberDataDefine.INSTANCE_4_1, "acCurrentA", "安培"),
//    // 交流 B 相电流
//    AC_CURRENT_B(0x74, NumberDataDefine.INSTANCE_4_1, "acCurrentB", "安培"),
//    // 交流 C 相电流
//    AC_CURRENT_C(0x75, NumberDataDefine.INSTANCE_4_1, "acCurrentC", "安培"),
//    ;
//
//    private final int flag;
//    private final IDataDefine<?> dataDefine;
//    private final String eleId;
//    private final String unitName;
//
//    EleIdentifierEnum(int flag, IDataDefine<?> dataDefine, String eleId, String unitName) {
//        this.flag = flag;
//        this.dataDefine = dataDefine;
//        this.eleId = eleId;
//        this.unitName = unitName;
//    }
//
//
//}
