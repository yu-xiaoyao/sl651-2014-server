package me.yuxiaoyao.sl651.netty.server.codec.de;

import com.tomgibara.bits.BitReader;
import com.tomgibara.bits.Bits;
import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.codec.AbstractStationFuncDecoder;
import me.yuxiaoyao.sl651.netty.server.enums.MessageType;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.BaseMessage;
import me.yuxiaoyao.sl651.netty.server.message.TelemetryStateAndAlarmMessage;
import me.yuxiaoyao.sl651.netty.server.message.item.TelemetryStateAndAlarmItem;
import me.yuxiaoyao.sl651.netty.server.util.ByteArrayUtil;

/**
 * @author kerryzhang on 2024/11/09
 */

public class QueryTelemetryStateAndAlarmFuncDecoder extends AbstractStationFuncDecoder {

    @Override
    public int getFuncCode() {
        // 6.6.4.20 中心站查询遥测站状态和报警信息
        return 0x46;
    }

    @Override
    protected BaseMessage decodeStationMessage(MessageHeader header, int serialNo, String sendTime, String stationId, ByteBuf buf) {
        TelemetryStateAndAlarmItem stateAndAlarm = parse(ByteArrayUtil.readByteBuf(buf, 4));
        return TelemetryStateAndAlarmMessage.builder()
                .serialNo(serialNo)
                .sendTime(sendTime)
                .telemetryId(stationId)
                .stateAndAlarm(stateAndAlarm)
                .build();
    }

    @Override
    public MessageType messageType() {
        return MessageType.READ;
    }

    public static TelemetryStateAndAlarmItem parse(byte[] bytes) {
        BitReader br = Bits.readerFrom(bytes);
        return TelemetryStateAndAlarmItem.builder()
                .acChargingState(br.readBit())
                .batteryVoltageState(br.readBit())
                .waterLevelAlarmState(br.readBit())
                .flowRateAlarmState(br.readBit())
                .waterQualityAlarmState(br.readBit())
                .flowMeterState(br.readBit())
                .waterLevelMeterState(br.readBit())
                .terminalBoxDoorState(br.readBit())
                .storageBoxState(br.readBit())
                .icCardFunctionState(br.readBit())
                .waterPumpState(br.readBit())
                .remainingWaterAlarmState(br.readBit())
                .build();
    }
}
