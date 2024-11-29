package me.yuxiaoyao.sl651.server.netty;


import lombok.RequiredArgsConstructor;
import me.yuxiaoyao.sl651.netty.server.event.DeviceEventPublisher;
import me.yuxiaoyao.sl651.netty.server.tcp.AbstractSL651TcpServer;
import me.yuxiaoyao.sl651.netty.server.tcp.HexSL651TcpServer;
import me.yuxiaoyao.sl651.server.properties.Sl261Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author kerryzhang on 2024/11/06
 */

@Component
@RequiredArgsConstructor
public class Sl651TcpServerStartup implements ApplicationRunner, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(Sl651TcpServerStartup.class);

    private final Sl261Properties properties;
    private final DeviceEventPublisher eventPublisher;

    private AbstractSL651TcpServer tcpServer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!properties.isEnable()) {
            return;
        }

        logger.info("start SL651-2014 on port = {}, eventPublisher = {}", properties.getPort(), eventPublisher.getClass());

        tcpServer = new HexSL651TcpServer(properties.getPort());
        tcpServer.setEventPublisher(this.eventPublisher);
        tcpServer.setReaderIdleTimeSeconds(properties.getReaderIdleTimeSeconds());
        tcpServer.setWriterIdleTimeSeconds(properties.getWriterIdleTimeSeconds());
        tcpServer.setAllIdleTimeSeconds(properties.getAllIdleTimeSeconds());
        tcpServer.setCrcErrorReSend(properties.isCrcErrorReSend());

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(tcpServer);
        executorService.shutdown();
    }

    @Override
    public void destroy() throws Exception {
        if (tcpServer != null) {
            tcpServer.shutdown();
        }
    }
}
