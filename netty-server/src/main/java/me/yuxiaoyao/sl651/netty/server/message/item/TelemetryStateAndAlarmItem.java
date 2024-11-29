package me.yuxiaoyao.sl651.netty.server.message.item;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author kerryzhang on 2024/11/09
 */


@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TelemetryStateAndAlarmItem {

    // 交流电充电状态
    private int acChargingState;

    // 蓄电池电压状态
    private int batteryVoltageState;

    // 水位超限报警状态
    private int waterLevelAlarmState;

    // 流量超限报警状态
    private int flowRateAlarmState;

    // 水质超限报警状态
    private int waterQualityAlarmState;

    // 流量仪表状态
    private int flowMeterState;

    // 水位仪表状态
    private int waterLevelMeterState;

    // 终端箱门状态
    private int terminalBoxDoorState;

    // 存储器状态
    private int storageBoxState;

    // IC 卡功能有效
    private int icCardFunctionState;

    // 水泵工作状态
    private int waterPumpState;

    // 剩余水量报警
    private int remainingWaterAlarmState;


}
