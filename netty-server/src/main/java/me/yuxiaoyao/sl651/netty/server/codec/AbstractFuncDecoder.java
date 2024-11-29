package me.yuxiaoyao.sl651.netty.server.codec;

import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.BaseMessage;
import me.yuxiaoyao.sl651.netty.server.util.ByteArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author KerryMiBook on 24/11/8
 */


public abstract class AbstractFuncDecoder implements BaseFuncDecoder<BaseMessage> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public BaseMessage decodeBody(MessageHeader header, int serialNo, String sendTime, ByteBuf buf) {
        // 遥测站地址, F1 F1
        var leaderFlag = ByteArrayUtil.readByteBuf(buf, 2);
        return decodeFirstLeaderFlag(header, serialNo, sendTime, leaderFlag, buf);
    }

    protected abstract BaseMessage decodeFirstLeaderFlag(MessageHeader header, int serialNo, String sendTime, byte[] leaderFlag, ByteBuf buf);



}
