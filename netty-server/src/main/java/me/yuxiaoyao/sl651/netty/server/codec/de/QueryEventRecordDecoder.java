package me.yuxiaoyao.sl651.netty.server.codec.de;


import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.codec.AbstractStationFuncDecoder;
import me.yuxiaoyao.sl651.netty.server.enums.MessageType;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.BaseMessage;
import me.yuxiaoyao.sl651.netty.server.message.EventRecordMessage;

/**
 * @author kerryzhang on 2024/11/21
 */

public class QueryEventRecordDecoder extends AbstractStationFuncDecoder {
    @Override
    protected BaseMessage decodeStationMessage(MessageHeader header, int serialNo, String sendTime, String telemetryId, ByteBuf buf) {

        var message = EventRecordMessage.builder()
                .serialNo(serialNo)
                .sendTime(sendTime)
                .telemetryId(telemetryId)
                .erc1(buf.readShort() & 0xFFFF)
                .erc2(buf.readShort() & 0xFFFF)
                .erc3(buf.readShort() & 0xFFFF)
                .erc4(buf.readShort() & 0xFFFF)
                .erc5(buf.readShort() & 0xFFFF)
                .erc6(buf.readShort() & 0xFFFF)
                .erc7(buf.readShort() & 0xFFFF)
                .erc8(buf.readShort() & 0xFFFF)
                .erc9(buf.readShort() & 0xFFFF)
                .erc10(buf.readShort() & 0xFFFF)
                .erc11(buf.readShort() & 0xFFFF)
                .erc12(buf.readShort() & 0xFFFF)
                .erc13(buf.readShort() & 0xFFFF)
                .erc14(buf.readShort() & 0xFFFF)
                .erc15(buf.readShort() & 0xFFFF)
                .erc16(buf.readShort() & 0xFFFF)
                .erc17(buf.readShort() & 0xFFFF)
                .erc18(buf.readShort() & 0xFFFF)
                .build();
        buf.skipBytes(14);
        return message;
    }

    @Override
    public int getFuncCode() {
        return 0x50;
    }

    @Override
    public MessageType messageType() {
        return MessageType.READ;
    }
}
