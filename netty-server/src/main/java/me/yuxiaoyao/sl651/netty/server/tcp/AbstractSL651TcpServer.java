package me.yuxiaoyao.sl651.netty.server.tcp;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Setter;
import me.yuxiaoyao.sl651.netty.server.event.LogDeviceEventPublisher;
import me.yuxiaoyao.sl651.netty.server.event.DeviceEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author kerryzhang on 2024/11/06
 */

public abstract class AbstractSL651TcpServer implements Runnable {

    public static final String INIT_IDLE_HANDLER_NAME = "InitIdleStateHandler";
    public static final String IDLE_HANDLER_NAME = "IdleStateHandler";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected final int port;
    @Setter
    protected String host;

    @Setter
    protected boolean crcErrorReSend = false;

    protected final NioEventLoopGroup bossLoopGroup;
    protected final NioEventLoopGroup workLoopGroup;

    protected Channel channel;

    /**
     * 这两个配置作用: enableConnectNoDataTimeout = true: 当设备连接上来后, connectNoDataTimeoutSeconds(10) 秒内没有数据就断开连接
     * 有一些非设备TCP连接上来, 避免占用服务器太久.
     */
    @Setter
    protected boolean enableConnectNoDataTimeout = false;
    @Setter
    protected int connectNoDataTimeoutSeconds = 10;

    /**
     * 当 enableConnectNoDataTimeout = true 时, 会在第一次数据到太时替换 IDLE 事件.避免还会关闭连接
     */
    @Setter
    protected int readerIdleTimeSeconds = 0;
    @Setter
    protected int writerIdleTimeSeconds = 0;
    @Setter
    protected int allIdleTimeSeconds = 180;

    @Setter
    protected DeviceEventPublisher eventPublisher = new LogDeviceEventPublisher();

    public AbstractSL651TcpServer(int port, int workerCount) {
        this.port = port;
        this.bossLoopGroup = new NioEventLoopGroup(1);
        this.workLoopGroup = new NioEventLoopGroup(workerCount);
    }

    public void start() {
        this.run();
    }

    public void shutdown() {
        bossLoopGroup.shutdownGracefully().syncUninterruptibly();
        workLoopGroup.shutdownGracefully().syncUninterruptibly();
        if (channel != null) {
            channel.closeFuture().syncUninterruptibly();
            channel = null;
        }
    }

    @Override
    public void run() {
        var serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossLoopGroup, workLoopGroup)
                .channel(NioServerSocketChannel.class)
                //.handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(getChannelInitializer());

        logger.info("starting tcp server on {}", port);
        try {

            if (host == null || host.isEmpty()) {
                channel = serverBootstrap.bind(port).sync().channel();
            } else {
                channel = serverBootstrap.bind(new InetSocketAddress(host, port)).sync().channel();
            }
            logger.info("start tcp server success on {}", port);
        } catch (InterruptedException e) {
            logger.error("start tcp server failed on {}. cause: {}", port, e.getMessage());
            e.printStackTrace();
        } catch (Throwable e) {
            logger.error("start tcp server failed. cause: {}", e.getMessage());
            throw e;
        }
    }


    protected abstract ChannelInitializer<SocketChannel> getChannelInitializer();
}
