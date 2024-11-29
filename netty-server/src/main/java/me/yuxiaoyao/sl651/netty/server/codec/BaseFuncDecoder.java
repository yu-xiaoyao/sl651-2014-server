package me.yuxiaoyao.sl651.netty.server.codec;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.yuxiaoyao.sl651.netty.server.enums.ControlChar;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.BaseMessage;
import me.yuxiaoyao.sl651.netty.server.message.ByteBufResponseMessage;
import me.yuxiaoyao.sl651.netty.server.message.ResponseMessage;
import me.yuxiaoyao.sl651.netty.server.util.HexUtil;

/**
 * @author kerryzhang on 2024/11/06
 */

public interface BaseFuncDecoder<T extends BaseMessage> extends IFuncDecoder<T>, IFuncCodeProvider {

    byte[] EMPTY_BODY = new byte[0];

    @Override
    default T decode(MessageHeader header, ByteBuf buf) {
        var serialNo = buf.readShort() & 0xFF;
        byte[] dateTime = new byte[6];
        buf.readBytes(dateTime);
        return decodeBody(header, serialNo, HexUtil.byteArray2HexStr(dateTime), buf);
    }

    T decodeBody(MessageHeader header, int serialNo, String sendTime, ByteBuf buf);


    @Override
    default ResponseMessage<?> makeResponse(MessageHeader header, T message) {
        if (!isMakeResponse()) {
            return null;
        }
        byte[] body = makeResponseBody(header, message);
        if (body == null || body.length == 0) {
            var byteBuf = Unpooled.buffer(8);
            byteBuf.writeShort(message.getSerialNo());
            byteBuf.writeBytes(HexUtil.hexStringToByteArray(message.getSendTime()));
            return ByteBufResponseMessage.builder()
                    .header(header)
                    .body(byteBuf)
                    .responsePacketEndCode(makeResponsePacketEndCode(header))
                    .build();
        }
        var byteBuf = Unpooled.buffer(8 + body.length);
        byteBuf.writeShort(message.getSerialNo());
        byteBuf.writeBytes(HexUtil.hexStringToByteArray(message.getSendTime()));
        byteBuf.writeBytes(body);
        return ByteBufResponseMessage.builder()
                .header(header)
                .body(byteBuf)
                .responsePacketEndCode(makeResponsePacketEndCode(header))
                .build();
    }

    default byte[] makeResponseBody(MessageHeader header, T message) {
        return EMPTY_BODY;
    }

    default int makeResponsePacketEndCode(MessageHeader header) {
        int packetEndCode = header.getPacketEndCode();
        if (packetEndCode == ControlChar.ETX) {
            return ControlChar.ESC;
        }
        // make more code match
        return header.getPacketEndCode();
    }

    default boolean isMakeResponse() {
        return messageType().hasReport();
    }

}
