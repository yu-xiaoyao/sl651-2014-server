package me.yuxiaoyao.sl651.netty.server.event;


import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;

/**
 * @author kerryzhang on 2024/11/06
 */

public interface DeviceEventPublisher {

    void onConnect(String id);

    void onMessageReport(MessageHeader header, Object decodeMessage);

    void onDisconnect(String id);
}
