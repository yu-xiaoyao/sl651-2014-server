package me.yuxiaoyao.sl651.netty.server.codec;


import me.yuxiaoyao.sl651.netty.server.message.send.ISendMessage;

/**
 * @author kerryzhang on 2024/11/06
 */

public interface IFuncEncoder<T extends ISendMessage> {

    byte[] encode(T sendMessage);

}
