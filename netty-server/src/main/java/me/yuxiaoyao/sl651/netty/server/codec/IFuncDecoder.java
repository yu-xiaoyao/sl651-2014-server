package me.yuxiaoyao.sl651.netty.server.codec;


import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.enums.MessageType;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.ResponseMessage;

/**
 * @author kerryzhang on 2024/11/06
 */

public interface IFuncDecoder<T> extends IFuncCodeProvider {

    T decode(MessageHeader header, ByteBuf buf);

    default ResponseMessage<?> makeResponseByObj(MessageHeader header, Object object) {
        return makeResponse(header, (T) object);
    }

    ResponseMessage<?> makeResponse(MessageHeader header, T message);

    default MessageType messageType() {
        return MessageType.REPORT;
    }

}
