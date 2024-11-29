package me.yuxiaoyao.sl651.netty.server.codec.en;


import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.codec.BaseFuncEncoder;
import me.yuxiaoyao.sl651.netty.server.message.send.UpdatePasswordSendMessage;
import me.yuxiaoyao.sl651.netty.server.util.HexUtil;

/**
 * @author kerryzhang on 2024/11/19
 */

public class UpdatePasswordEncoder implements BaseFuncEncoder<UpdatePasswordSendMessage> {

    public static UpdatePasswordEncoder INSTANCE = new UpdatePasswordEncoder();

    @Override
    public void encodeBody(ByteBuf bodyBuffer, UpdatePasswordSendMessage sendMessage) {

        // old password
        bodyBuffer.writeByte(0x03);    // 密码标识符
        bodyBuffer.writeByte(0x10);    // 长度
        bodyBuffer.writeBytes(HexUtil.hexStringToByteArray(sendMessage.getOldPassword()));

        // new password
        bodyBuffer.writeByte(0x03);    // 密码标识符
        bodyBuffer.writeByte(0x10);    // 长度
        bodyBuffer.writeBytes(HexUtil.hexStringToByteArray(sendMessage.getNewPassword()));
    }
}
