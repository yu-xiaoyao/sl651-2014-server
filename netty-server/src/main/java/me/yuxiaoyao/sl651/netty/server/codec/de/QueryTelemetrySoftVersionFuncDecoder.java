package me.yuxiaoyao.sl651.netty.server.codec.de;

import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.codec.AbstractStationFuncDecoder;
import me.yuxiaoyao.sl651.netty.server.enums.MessageType;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.BaseMessage;
import me.yuxiaoyao.sl651.netty.server.message.TelemetrySoftVersionMessage;

import java.nio.charset.StandardCharsets;

/**
 * @author kerryzhang on 2024/11/09
 */

public class QueryTelemetrySoftVersionFuncDecoder extends AbstractStationFuncDecoder {

    @Override
    public int getFuncCode() {
        // 6.6.4.19 中心站查询遥测站软件版本
        return 0x45;
    }

    @Override
    protected BaseMessage decodeStationMessage(MessageHeader header, int serialNo, String sendTime, String stationId, ByteBuf buf) {
        int versionLen = buf.readByte() & 0xFF;
        byte[] bytes = new byte[versionLen];
        buf.readBytes(bytes);
        String data = new String(bytes, StandardCharsets.UTF_8);
        return TelemetrySoftVersionMessage.builder()
                .serialNo(serialNo)
                .sendTime(sendTime)
                .telemetryId(stationId)
                .version(data)
                .build();
    }

    @Override
    public MessageType messageType() {
        return MessageType.READ;
    }
}
