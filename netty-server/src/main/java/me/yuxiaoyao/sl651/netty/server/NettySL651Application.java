package me.yuxiaoyao.sl651.netty.server;


import me.yuxiaoyao.sl651.netty.server.event.LogDeviceEventPublisher;
import me.yuxiaoyao.sl651.netty.server.tcp.HexSL651TcpServer;

/**
 * @author kerryzhang on 2024/11/06
 */

public class NettySL651Application {

    public static void main(String[] args) {
        var tcpServer = new HexSL651TcpServer(5555);
        // 设置事件发布,收到消息协议解析后触发.
        tcpServer.setEventPublisher(new LogDeviceEventPublisher());
        tcpServer.start();
    }

}
