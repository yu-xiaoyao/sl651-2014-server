package me.yuxiaoyao.sl651.netty.server.message;


/**
 * @author kerryzhang on 2024/11/11
 */

public interface IM3Message extends IMessage {

    int getTotal();

    int getSeq();

    byte[] getData();

}
