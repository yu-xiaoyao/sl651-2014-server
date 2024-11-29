package me.yuxiaoyao.sl651.netty.server.event;


import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;

/**
 * @author kerryzhang on 2024/11/13
 */

public class DeviceEventPublisherAdapter implements DeviceEventPublisher {
    @Override
    public void onConnect(String id) {

    }

    @Override
    public void onMessageReport(MessageHeader header, Object decodeMessage) {

    }

    @Override
    public void onDisconnect(String id) {

    }
}
