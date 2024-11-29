package me.yuxiaoyao.sl651.netty.server.codec;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.yuxiaoyao.sl651.netty.server.enums.ControlChar;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.header.Messages;
import me.yuxiaoyao.sl651.netty.server.message.send.ISendMessage;
import me.yuxiaoyao.sl651.netty.server.util.CrcUtil;
import me.yuxiaoyao.sl651.netty.server.util.HexUtil;

/**
 * @author kerryzhang on 2024/11/06
 */

public interface BaseFuncEncoder<T extends ISendMessage> extends IFuncEncoder<T> {

    @Override
    default byte[] encode(T sendMessage) {
        MessageHeader header = sendMessage.getHeader();
        ByteBuf bodyBuffer = Unpooled.buffer();
        bodyBuffer.writeShort(sendMessage.getSerialNo());
        bodyBuffer.writeBytes(HexUtil.hexStringToByteArray(sendMessage.getSendTime()));
        this.encodeBody(bodyBuffer, sendMessage);

        byte[] body = new byte[bodyBuffer.readableBytes()];
        bodyBuffer.readBytes(body);
        bodyBuffer.release();


        ByteBuf buffer = Unpooled.buffer();
        buffer.writeBytes(Messages.START_CODE);
        buffer.writeBytes(HexUtil.hexStringToByteArray(header.getTelemetryId()));
        buffer.writeByte(header.getStationId() & 0xFF);
        buffer.writeBytes(HexUtil.hexStringToByteArray(header.getPassword()));
        // fun code
        buffer.writeByte(header.getFuncCode());
        // dataLen 2 bytes
        int dataLen = (0x08 << 12) | (body.length & 0xFFF);
        buffer.writeShort(dataLen);
        // 数据起始符
        buffer.writeByte(ControlChar.STX);
        // body
        buffer.writeBytes(body);
        // 结束符
        buffer.writeByte(ControlChar.ENQ);

        byte[] data = new byte[buffer.readableBytes()];
        buffer.readBytes(data);
        buffer.release();
        byte[] crc = CrcUtil.calcCrc16Bytes(data);
        return HexUtil.concat(data, crc);
    }

    default void encodeBody(ByteBuf bodyBuffer, T sendMessage) {
    }

}
