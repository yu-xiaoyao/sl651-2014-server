package me.yuxiaoyao.sl651.netty.server.message.send;


import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.IMessage;

/**
 * @author kerryzhang on 2024/11/18
 */

public interface ISendMessage extends IMessage {

    MessageHeader getHeader();

    String getSendTime();

    int getSerialNo();
}
