package me.yuxiaoyao.sl651.netty.server.element;


import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.message.item.ElementItem;
import me.yuxiaoyao.sl651.netty.server.read.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

/**
 * @author kerryzhang on 2024/11/20
 */

public class ElementReaders {

    private static final Logger logger = LoggerFactory.getLogger(ElementReaders.class);

    private static final Map<Integer, IObjectReader<?>> DD_MAP = new ConcurrentHashMap<>();

    static {
        initDataDefine();
    }

    public static IObjectReader<?> getObjectReader(int flag) {
        return DD_MAP.get(flag);
    }

    private static void initDataDefine() {

        // 观测时间引导符
        DD_MAP.put(0xF0, new ObservationTimeReader());
        // 测站编码引导符
        //DD_MAP.put(0xF1, new TelemetryStationDataDefine(0xF1));
        // 人工置数
        //DD_MAP.put(0xF2, new ManualSetDataDefine(0xF2));
        // 1 小时内每 5 分钟时段雨量 e（每组雨量占 1 字节 HEX，最大值 25.4 毫米，数据中不含小数点；FFH表示非法数据。）
        DD_MAP.put(0xF4, new DynamicByteItemCountNumberReader(12, 1));
        // 1 小时内 5 分钟间隔相对水位
        DD_MAP.put(0xF5, new DynamicByteItemCountNumberReader(12, 2));

        // --------------------------------
        // 断面面积
        DD_MAP.put(0x01, DynamicNumberReader.INSTANCE_8_2);
        // 瞬时气温
        DD_MAP.put(0x02, DynamicNumberReader.INSTANCE_3_1);
        // 瞬时水温
        DD_MAP.put(0x03, DynamicNumberReader.INSTANCE_3_1);
        // 时间步长码
        DD_MAP.put(0x04, new DynamicTimeStepReader());
        // 时段长
        DD_MAP.put(0x05, TimeHHmmReader.INSTANCE);
        // 日蒸发量
        DD_MAP.put(0x06, DynamicNumberReader.INSTANCE_5_1);
        // 当前蒸发
        DD_MAP.put(0x07, DynamicNumberReader.INSTANCE_5_1);
        // 气压
        DD_MAP.put(0x08, DynamicNumberReader.INSTANCE_5_0);
        // 闸坝、水库闸门开启高度
        DD_MAP.put(0x09, DynamicNumberReader.INSTANCE_5_2);
        // 输水设备、闸门(组)编号
        DD_MAP.put(0x0A, DynamicNumberReader.INSTANCE_3_0);
        // 输水设备类别
        DD_MAP.put(0x0B, new DynamicNumberReader(1, 0));
        // 水库、闸坝闸门开启孔数
        DD_MAP.put(0x0C, DynamicNumberReader.INSTANCE_3_0);
        // 地温
        DD_MAP.put(0x0D, DynamicNumberReader.INSTANCE_3_1);
        // 地下水瞬时埋深
        DD_MAP.put(0x0E, DynamicNumberReader.INSTANCE_6_2);
        // 波浪高度
        DD_MAP.put(0x0F, DynamicNumberReader.INSTANCE_5_2);
        // 10 厘米处土壤含水量
        DD_MAP.put(0x10, DynamicNumberReader.INSTANCE_4_1);
        // 20 厘米处土壤含水量
        DD_MAP.put(0x11, DynamicNumberReader.INSTANCE_4_1);
        // 30 厘米处土壤含水量
        DD_MAP.put(0x12, DynamicNumberReader.INSTANCE_4_1);
        // 40 厘米处土壤含水量
        DD_MAP.put(0x13, DynamicNumberReader.INSTANCE_4_1);
        // 50 厘米处土壤含水量
        DD_MAP.put(0x14, DynamicNumberReader.INSTANCE_4_1);
        // 60 厘米处土壤含水量
        DD_MAP.put(0x15, DynamicNumberReader.INSTANCE_4_1);
        // 80 厘米处土壤含水量
        DD_MAP.put(0x16, DynamicNumberReader.INSTANCE_4_1);
        // 100 厘米处土壤含水量
        DD_MAP.put(0x17, DynamicNumberReader.INSTANCE_4_1);
        // 湿度
        DD_MAP.put(0x18, DynamicNumberReader.INSTANCE_4_1);
        // 开机台数
        DD_MAP.put(0x19, DynamicNumberReader.INSTANCE_2_0);
        // 1 小时时段降水量
        DD_MAP.put(0x1A, DynamicNumberReader.INSTANCE_5_1);
        // 2 小时时段降水量
        DD_MAP.put(0x1B, DynamicNumberReader.INSTANCE_5_1);
        // 3 小时时段降水量
        DD_MAP.put(0x1C, DynamicNumberReader.INSTANCE_5_1);
        // 6 小时时段降水量
        DD_MAP.put(0x1D, DynamicNumberReader.INSTANCE_5_1);
        // 12 小时时段降水量
        DD_MAP.put(0x1E, DynamicNumberReader.INSTANCE_5_1);
        // 日降水量
        DD_MAP.put(0x1F, DynamicNumberReader.INSTANCE_5_1);
        // 当前降水量
        DD_MAP.put(0x20, DynamicNumberReader.INSTANCE_5_1);
        // 1 分钟时段降水量
        DD_MAP.put(0x21, DynamicNumberReader.INSTANCE_5_1);
        // 5 分钟时段降水量
        DD_MAP.put(0x22, DynamicNumberReader.INSTANCE_5_1);
        // 10 分钟时段降水量
        DD_MAP.put(0x23, DynamicNumberReader.INSTANCE_5_1);
        // 30 分钟时段降水量
        DD_MAP.put(0x24, DynamicNumberReader.INSTANCE_5_1);
        // 暴雨量
        DD_MAP.put(0x25, DynamicNumberReader.INSTANCE_5_1);
        // 降水量累计值
        DD_MAP.put(0x26, DynamicNumberReader.INSTANCE_6_1);
        // 瞬时流量、抽水流量
        DD_MAP.put(0x27, DynamicNumberReader.INSTANCE_9_3);
        // 取(排）水口流量 1
        DD_MAP.put(0x28, DynamicNumberReader.INSTANCE_9_3);
        // 取(排）水口流量 2
        DD_MAP.put(0x29, DynamicNumberReader.INSTANCE_9_3);
        // 取(排）水口流量 3
        DD_MAP.put(0x2A, DynamicNumberReader.INSTANCE_9_3);
        // 取(排）水口流量 4
        DD_MAP.put(0x2B, DynamicNumberReader.INSTANCE_9_3);
        // 取(排）水口流量 5
        DD_MAP.put(0x2C, DynamicNumberReader.INSTANCE_9_3);
        // 取(排）水口流量 6
        DD_MAP.put(0x2D, DynamicNumberReader.INSTANCE_9_3);
        // 取(排）水口流量 7
        DD_MAP.put(0x2E, DynamicNumberReader.INSTANCE_9_3);
        // 取(排）水口流量 8
        DD_MAP.put(0x2F, DynamicNumberReader.INSTANCE_9_3);
        // 总出库流量、过闸总流量
        DD_MAP.put(0x30, DynamicNumberReader.INSTANCE_9_3);
        // 输水设备流量、过闸(组)流量
        DD_MAP.put(0x31, DynamicNumberReader.INSTANCE_9_3);
        // 输沙量
        DD_MAP.put(0x32, DynamicNumberReader.INSTANCE_11_3);
        // 风向
        DD_MAP.put(0x33, DynamicNumberReader.INSTANCE_2_0);
        // 风力(级)
        DD_MAP.put(0x34, DynamicNumberReader.INSTANCE_2_0);
        // 风速
        DD_MAP.put(0x35, DynamicNumberReader.INSTANCE_4_1);
        // 断面平均流速
        DD_MAP.put(0x36, DynamicNumberReader.INSTANCE_5_3);
        // 当前瞬时流速
        DD_MAP.put(0x37, DynamicNumberReader.INSTANCE_5_3);
        // 电源电压
        DD_MAP.put(0x38, DynamicNumberReader.INSTANCE_4_2);
        // 瞬时河道水位、潮位
        DD_MAP.put(0x39, DynamicNumberReader.INSTANCE_7_3);
        // 库(闸、站)下水位
        DD_MAP.put(0x3A, DynamicNumberReader.INSTANCE_7_3);
        // 库(闸、站)上水位
        DD_MAP.put(0x3B, DynamicNumberReader.INSTANCE_7_3);
        // 取(排）水口水位 1
        DD_MAP.put(0x3C, DynamicNumberReader.INSTANCE_7_3);
        // 取(排）水口水位 2
        DD_MAP.put(0x3D, DynamicNumberReader.INSTANCE_7_3);
        // 取(排）水口水位 3
        DD_MAP.put(0x3E, DynamicNumberReader.INSTANCE_7_3);
        // 取(排）水口水位 4
        DD_MAP.put(0x3F, DynamicNumberReader.INSTANCE_7_3);
        // 取(排）水口水位 5
        DD_MAP.put(0x40, DynamicNumberReader.INSTANCE_7_3);
        // 取(排）水口水位 6
        DD_MAP.put(0x41, DynamicNumberReader.INSTANCE_7_3);
        // 取(排）水口水位 7
        DD_MAP.put(0x42, DynamicNumberReader.INSTANCE_7_3);
        // 取(排）水口水位 8
        DD_MAP.put(0x43, DynamicNumberReader.INSTANCE_7_3);
        // 含沙量
        DD_MAP.put(0x44, DynamicNumberReader.INSTANCE_9_3);

        // 遥测站状态及报警信息（定义见表 58）
        // 直接使用 QueryTelemetryStateAndAlarmFuncDecoder 解析.
        // TELEMETRY_STATE_AND_ALARM(0x45, "ZT", new TelemetryStateAndAlarmDataDefine(), "telecomStateAndAlarm", null),

        // pH 值
        DD_MAP.put(0x46, DynamicNumberReader.INSTANCE_4_2);
        // 溶解氧
        DD_MAP.put(0x47, DynamicNumberReader.INSTANCE_4_2);
        // 电导率
        DD_MAP.put(0x48, DynamicNumberReader.INSTANCE_5_0);
        // 浊度
        DD_MAP.put(0x49, DynamicNumberReader.INSTANCE_3_0);
        // 高锰酸盐指数
        DD_MAP.put(0x4A, DynamicNumberReader.INSTANCE_4_1);
        // 氧化还原电位
        DD_MAP.put(0x4B, DynamicNumberReader.INSTANCE_5_1);
        // 氨氮
        DD_MAP.put(0x4C, DynamicNumberReader.INSTANCE_6_2);
        // 总磷
        DD_MAP.put(0x4D, DynamicNumberReader.INSTANCE_5_3);
        // 总氮
        DD_MAP.put(0x4E, DynamicNumberReader.INSTANCE_5_2);
        // 总有机碳
        DD_MAP.put(0x4F, DynamicNumberReader.INSTANCE_4_2);
        // 铜
        DD_MAP.put(0x50, DynamicNumberReader.INSTANCE_7_4);
        // 锌
        DD_MAP.put(0x51, DynamicNumberReader.INSTANCE_6_4);
        // 硒
        DD_MAP.put(0x52, DynamicNumberReader.INSTANCE_7_5);
        // 砷
        DD_MAP.put(0x53, DynamicNumberReader.INSTANCE_7_5);
        // 总汞
        DD_MAP.put(0x54, DynamicNumberReader.INSTANCE_7_5);
        // 镉
        DD_MAP.put(0x55, DynamicNumberReader.INSTANCE_7_5);
        // 铅
        DD_MAP.put(0x56, DynamicNumberReader.INSTANCE_7_5);
        // 叶绿素 a
        DD_MAP.put(0x57, DynamicNumberReader.INSTANCE_4_2);
        // 水压 1
        DD_MAP.put(0x58, DynamicNumberReader.INSTANCE_5_2);
        // 水压 2
        DD_MAP.put(0x59, DynamicNumberReader.INSTANCE_5_2);
        // 水压 3
        DD_MAP.put(0x5A, DynamicNumberReader.INSTANCE_5_2);
        // 水压 4
        DD_MAP.put(0x5B, DynamicNumberReader.INSTANCE_5_2);
        // 水压 5
        DD_MAP.put(0x5C, DynamicNumberReader.INSTANCE_5_2);
        // 水压 6
        DD_MAP.put(0x5D, DynamicNumberReader.INSTANCE_5_2);
        // 水压 7
        DD_MAP.put(0x5E, DynamicNumberReader.INSTANCE_5_2);
        // 水压 8
        DD_MAP.put(0x5F, DynamicNumberReader.INSTANCE_5_2);
        // 水表 1 剩余水量
        DD_MAP.put(0x60, DynamicNumberReader.INSTANCE_11_3);
        // 水表 2 剩余水量
        DD_MAP.put(0x61, DynamicNumberReader.INSTANCE_11_3);
        // 水表 3 剩余水量
        DD_MAP.put(0x62, DynamicNumberReader.INSTANCE_11_3);
        // 水表 4 剩余水量
        DD_MAP.put(0x63, DynamicNumberReader.INSTANCE_11_3);
        // 水表 5 剩余水量
        DD_MAP.put(0x64, DynamicNumberReader.INSTANCE_11_3);
        // 水表 6 剩余水量
        DD_MAP.put(0x65, DynamicNumberReader.INSTANCE_11_3);
        // 水表 7 剩余水量
        DD_MAP.put(0x66, DynamicNumberReader.INSTANCE_11_3);
        // 水表 8 剩余水量
        DD_MAP.put(0x67, DynamicNumberReader.INSTANCE_11_3);

        // 水表 1 每小时水量
        DD_MAP.put(0x68, DynamicNumberReader.INSTANCE_10_2);
        // 水表 2 每小时水量
        DD_MAP.put(0x69, DynamicNumberReader.INSTANCE_10_2);
        // 水表 3 每小时水量
        DD_MAP.put(0x6A, DynamicNumberReader.INSTANCE_10_2);
        // 水表 4 每小时水量
        DD_MAP.put(0x6B, DynamicNumberReader.INSTANCE_10_2);
        // 水表 5 每小时水量
        DD_MAP.put(0x6C, DynamicNumberReader.INSTANCE_10_2);
        // 水表 6 每小时水量
        DD_MAP.put(0x6D, DynamicNumberReader.INSTANCE_10_2);
        // 水表 7 每小时水量
        DD_MAP.put(0x6E, DynamicNumberReader.INSTANCE_10_2);
        // 水表 8 每小时水量
        DD_MAP.put(0x6F, DynamicNumberReader.INSTANCE_10_2);

        // 交流 A 相电压
        DD_MAP.put(0x70, DynamicNumberReader.INSTANCE_4_1);
        // 交流 B 相电压
        DD_MAP.put(0x71, DynamicNumberReader.INSTANCE_4_1);
        // 交流 C 相电压
        DD_MAP.put(0x72, DynamicNumberReader.INSTANCE_4_1);
        // 交流 A 相电流
        DD_MAP.put(0x73, DynamicNumberReader.INSTANCE_4_1);
        // 交流 B 相电流
        DD_MAP.put(0x74, DynamicNumberReader.INSTANCE_4_1);
        // 交流 C 相电流
        DD_MAP.put(0x75, DynamicNumberReader.INSTANCE_4_1);

    }


    public static List<ElementItem> decodeEleDeviceList(String observerTime, ByteBuf buf) {
        List<ElementItem> deviceDataList = new ArrayList<>();

        while (buf.isReadable()) {
            int flag = buf.readByte() & 0xFF;
            if (flag == 0xFF) {
                // 用户自定义 in next byte
                flag = buf.readByte() & 0xFF;
            }

            var reader = getObjectReader(flag);
            if (reader == null) {
                if (flag >= 0x76 && flag <= 0xEF) {
                    // ignore
                    logger.warn("保留,其他要素标识符扩展定义. 如有定义,请添加在: EleIdentifierEnum 中. flag: 0x{}", Integer.toHexString(flag));
                    continue;
                } else {
                    throw new RuntimeException("未知要素标识符解析. 请检查 EleIdentifierEnum 中的对应关系是否缺少. flag: 0x" + Integer.toHexString(flag));
                }
            }

            // 时段数据,会在后面数据带上时间........
            if (flag == 0xF0) {
                observerTime = (String) reader.read(buf);
                continue;
            }

            logger.atTrace().log("开始解析. observerTime = {}, flag = 0x{}, define = {}", observerTime, Integer.toHexString(flag), reader);

            var ret = reader.read(buf);

            logger.atTrace().log("完成解析. observerTime = {}, ret = {}", observerTime, ret);
            if (ret != null) {
                /*if (observerTime != null) {
                    deviceDataList.add(new ElementItem(observerTime, flag, ret));
                } else {
                    logger.warn("数据异常. 观测时间为空. flag = 0x{}, data = {}", Integer.toHexString(flag), ret);
                }*/
                deviceDataList.add(new ElementItem(observerTime, flag, ret));
            } else {
                logger.warn("数据异常. 解析结果为空. flag = 0x{}", Integer.toHexString(flag));
            }

        }
        return deviceDataList;
    }


    public static List<ElementItem> decodeCodingElements(ByteBuf buf) {
        List<ElementItem> elements = new ArrayList<>();

        while (buf.isReadable()) {
            int flag = buf.readByte() & 0xFF;
            if (flag == 0xFF) {
                // 用户自定义 in next byte
                flag = buf.readByte() & 0xFF;
            }

            var dataDefine = getObjectReader(flag);
            if (dataDefine == null) {
                if (flag >= 0x76 && flag <= 0xEF) {
                    // ignore
                    logger.warn("保留,其他要素标识符扩展定义. 如有定义,请添加在: EleIdentifierEnum 中. flag: 0x{}", Integer.toHexString(flag));
                    continue;
                } else {
                    throw new RuntimeException("未知要素标识符解析. 请检查 EleIdentifierEnum 中的对应关系是否缺少. flag: 0x" + Integer.toHexString(flag));
                }
            }

            var ret = dataDefine.read(buf);
            if (ret != null) {
                elements.add(new ElementItem(null, flag, ret));
            } else {
                logger.warn("数据异常. 解析结果为空. flag = 0x{}", Integer.toHexString(flag));
            }
        }
        return elements;
    }

    public static List<ElementItem> decodeElements(ByteBuf buf, BiFunction<Integer, Object, ElementItem> function) {
        List<ElementItem> elements = new ArrayList<>();

        while (buf.isReadable()) {
            int flag = buf.readByte() & 0xFF;
            if (flag == 0xFF) {
                // 用户自定义 in next byte
                flag = buf.readByte() & 0xFF;
            }

            var dataDefine = getObjectReader(flag);
            if (dataDefine == null) {
                if (flag >= 0x76 && flag <= 0xEF) {
                    // ignore
                    logger.warn("保留,其他要素标识符扩展定义. 如有定义,请添加在: EleIdentifierEnum 中. flag: 0x{}", Integer.toHexString(flag));
                    continue;
                } else {
                    throw new RuntimeException("未知要素标识符解析. 请检查 EleIdentifierEnum 中的对应关系是否缺少. flag: 0x" + Integer.toHexString(flag));
                }
            }

            var ret = dataDefine.read(buf);
            if (ret != null) {
                // return new ElementItem(null, flag, ret)
                var apply = function.apply(flag, ret);
                if (apply != null) {
                    elements.add(apply);
                }
            } else {
                logger.warn("数据异常. 解析结果为空. flag = 0x{}", Integer.toHexString(flag));
            }
        }
        return elements;
    }


  /*  @Getter
    public static abstract class RemoveElementAction {
        private final int flag;

        public RemoveElementAction(EleIdentifierEnum flagEnum) {
            this(flagEnum.getFlag());
        }

        public RemoveElementAction(int flag) {
            this.flag = flag;
        }

        public abstract void onRemove(ElementItem element);
    }*/

   /* public static void handleRemoveCodingElements(String observerTime, List<ElementItem> elements, RemoveElementAction... actions) {
        Map<Integer, RemoveElementAction> actionMap = new HashMap<>(actions.length);
        for (RemoveElementAction action : actions) {
            actionMap.put(action.getFlag(), action);
        }

        Iterator<ElementItem> iterator = elements.iterator();
        while (iterator.hasNext()) {
            ElementItem element = iterator.next();
            Integer eleId = element.getEleId();
            if (eleId == EleIdentifierEnum.OBSERVATION_TIME.getFlag()) {
                observerTime = (String) element.getValue();
                iterator.remove();
            } else {
                // set observerTime
                element.setObserverTime(observerTime);
                var remoteElementAction = actionMap.get(eleId);
                if (remoteElementAction != null) {
                    remoteElementAction.onRemove(element);
                    iterator.remove();
                }
            }
        }
    }*/

   /* public static void handleCodingElements(String observerTime, List<ElementItem> elements, Consumer<ElementItem> removeAction, EleIdentifierEnum... removeIds) {
        Iterator<ElementItem> iterator = elements.iterator();

        while (iterator.hasNext()) {
            ElementItem element = iterator.next();
            Integer eleId = element.getEleId();
            if (eleId == EleIdentifierEnum.OBSERVATION_TIME.getFlag()) {
                observerTime = (String) element.getValue();
                iterator.remove();
            } else {
                // set observerTime
                element.setObserverTime(observerTime);
                for (EleIdentifierEnum removeId : removeIds) {
                    if (eleId == removeId.getFlag()) {
                        removeAction.accept(element);
                        iterator.remove();
                    }
                }
            }
        }
    }*/

}
