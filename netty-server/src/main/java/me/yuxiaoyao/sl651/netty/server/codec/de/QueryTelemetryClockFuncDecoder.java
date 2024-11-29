package me.yuxiaoyao.sl651.netty.server.codec.de;

import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.codec.AbstractStationFuncDecoder;
import me.yuxiaoyao.sl651.netty.server.enums.MessageType;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.BaseMessage;
import me.yuxiaoyao.sl651.netty.server.message.TelemetryIdMessage;

/**
 * @author kerryzhang on 2024/11/09
 */

public class QueryTelemetryClockFuncDecoder extends AbstractStationFuncDecoder {
    @Override
    public int getFuncCode() {
        // 6.6.4.31 中心站查询遥测站时钟
        return 0x51;
    }

    @Override
    protected BaseMessage decodeStationMessage(MessageHeader header, int serialNo, String sendTime, String telemetryId, ByteBuf buf) {
        return TelemetryIdMessage.builder()
                .serialNo(serialNo)
                .sendTime(sendTime)
                .telemetryId(telemetryId)
                .build();
    }

    @Override
    public MessageType messageType() {
        return MessageType.READ;
    }
}
