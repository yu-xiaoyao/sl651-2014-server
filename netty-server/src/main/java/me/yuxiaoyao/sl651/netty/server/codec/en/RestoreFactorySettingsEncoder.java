package me.yuxiaoyao.sl651.netty.server.codec.en;


import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.codec.BaseFuncEncoder;
import me.yuxiaoyao.sl651.netty.server.message.send.BaseSendMessage;

/**
 * @author kerryzhang on 2024/11/19
 */

public class RestoreFactorySettingsEncoder implements BaseFuncEncoder<BaseSendMessage> {

    public static RestoreFactorySettingsEncoder INSTANCE = new RestoreFactorySettingsEncoder();

    @Override
    public void encodeBody(ByteBuf bodyBuffer, BaseSendMessage sendMessage) {
        // 出厂重置标识符(98)+00
        bodyBuffer.writeByte(0x98);
        bodyBuffer.writeByte(0x00);
    }
}
