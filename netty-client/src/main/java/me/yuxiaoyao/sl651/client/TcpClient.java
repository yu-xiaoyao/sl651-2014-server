package me.yuxiaoyao.sl651.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author KerryMiBook on 24/11/8
 */


public class TcpClient implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(TcpClient.class);

    private final String host;
    private final int port;

    private final EventLoopGroup group;
    private Channel channel;


    public TcpClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.group = new NioEventLoopGroup();
    }


    public void start() {
        this.run();
    }

    @Override
    public void run() {
        var bootstrap = new Bootstrap();
        bootstrap
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new TcpClientHandler());
                    }
                });

        logger.info("开始连接. {}:{}", host, port);
        try {
            channel = bootstrap.connect(new InetSocketAddress(host, port))
                    .addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            logger.info("连接结果. {}", channelFuture.isSuccess());
                        }
                    })
                    .sync()
                    .channel();
        } catch (InterruptedException e) {
            logger.error("连接失败. {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public void close() {
        group.shutdownGracefully().syncUninterruptibly();
        if (channel != null) {
            channel.closeFuture().syncUninterruptibly();
            channel = null;
        }
    }

    @SneakyThrows
    public boolean sendHex(String hex) {
        return sendBytes(Hex.decodeHex(hex));
    }

    public boolean sendBytes(byte[] bytes) {
        if (channel != null && channel.isOpen() && channel.isActive() && channel.isWritable()) {
            logger.info("发送: {}", Hex.encodeHexString(bytes));
            channel.writeAndFlush(Unpooled.copiedBuffer(bytes));
            return true;
        }
        return false;
    }
}
