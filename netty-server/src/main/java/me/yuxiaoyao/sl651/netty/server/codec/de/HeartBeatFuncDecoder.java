package me.yuxiaoyao.sl651.netty.server.codec.de;


import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.codec.BaseFuncDecoder;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.BaseMessage;

/**
 * @author kerryzhang on 2024/11/06
 */

public class HeartBeatFuncDecoder implements BaseFuncDecoder<BaseMessage> {

    @Override
    public BaseMessage decodeBody(MessageHeader header, int serialNo, String sendTime, ByteBuf buf) {
        return new BaseMessage(serialNo, sendTime);
    }

    @Override
    public int getFuncCode() {
        // 6.6.4.2 链路维持报
        return 0x2F;
    }

    @Override
    public BaseMessage decode(MessageHeader header, ByteBuf buf) {
        // 不需要回复
        return null;
    }
}
