package me.yuxiaoyao.sl651.netty.server.codec;

import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.BaseMessage;
import me.yuxiaoyao.sl651.netty.server.message.ResponseMessage;

/**
 * @author kerryzhang on 2024/11/10
 */

public interface NoResponseFuncDecoder {

    /**
     * hook. {@link BaseFuncDecoder#makeResponse(MessageHeader, BaseMessage)}
     *
     * @param header
     * @param object
     * @return
     */
    default ResponseMessage<?> makeResponseByObj(MessageHeader header, Object object) {
        return null;
    }

}
