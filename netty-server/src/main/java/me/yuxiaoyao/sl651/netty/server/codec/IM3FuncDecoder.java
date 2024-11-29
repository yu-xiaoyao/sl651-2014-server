package me.yuxiaoyao.sl651.netty.server.codec;


import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.ResponseMessage;

/**
 * @author kerryzhang on 2024/11/11
 */

public interface IM3FuncDecoder<T> {

    T decode(MessageHeader header, ByteBuf buf, int total, int seqNo);

    default ResponseMessage<?> makeResponseByObj(MessageHeader header, Object object) {
        return makeResponse(header, (T) object);
    }

    ResponseMessage<?> makeResponse(MessageHeader header, T message);

}
