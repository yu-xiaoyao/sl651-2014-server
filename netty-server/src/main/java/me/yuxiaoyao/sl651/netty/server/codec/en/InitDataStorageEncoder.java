package me.yuxiaoyao.sl651.netty.server.codec.en;


import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.codec.BaseFuncEncoder;
import me.yuxiaoyao.sl651.netty.server.message.send.BaseSendMessage;

/**
 * @author kerryzhang on 2024/11/19
 */

public class InitDataStorageEncoder implements BaseFuncEncoder<BaseSendMessage> {

    public static InitDataStorageEncoder INSTANCE = new InitDataStorageEncoder();

    @Override
    public void encodeBody(ByteBuf bodyBuffer, BaseSendMessage sendMessage) {
        bodyBuffer.writeByte(0x97);
        bodyBuffer.writeByte(0x00);
    }
}
