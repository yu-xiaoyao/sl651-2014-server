package me.yuxiaoyao.sl651.netty.server.tcp;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import me.yuxiaoyao.sl651.netty.server.tcp.hex.SL651TcpInboundHandler;
import me.yuxiaoyao.sl651.netty.server.tcp.hex.SL651TcpOutboundHandler;

/**
 * @author kerryzhang on 2024/11/06
 */

public class HexSL651TcpServer extends AbstractSL651TcpServer implements Runnable {

    public HexSL651TcpServer(int port) {
        super(port, 0);
    }

    public HexSL651TcpServer(int port, int workerCount) {
        super(port, workerCount);
    }

    @Override
    protected ChannelInitializer<SocketChannel> getChannelInitializer() {
        return new ChannelInitializer<>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline pipeline = socketChannel.pipeline();
                // 协议定义最大为 3 + 5 + 2 + 1 + 2 + 1 + 4195 + 1 + 2  = 4212
                pipeline.addLast(new LengthFieldBasedFrameDecoder(8192, 11, 2, 3 + 1, 0));
                if (enableConnectNoDataTimeout) {
                    pipeline.addLast(INIT_IDLE_HANDLER_NAME, new IdleStateHandler(0, 0, connectNoDataTimeoutSeconds));
                } else {
                    pipeline.addLast(IDLE_HANDLER_NAME, new IdleStateHandler(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds));
                }
                pipeline.addLast(new SL651TcpOutboundHandler());

                SL651TcpInboundHandler businessHandler;
                if (enableConnectNoDataTimeout) {
                    businessHandler = new SL651TcpInboundHandler(eventPublisher, crcErrorReSend, readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds);
                } else {
                    businessHandler = new SL651TcpInboundHandler(eventPublisher, crcErrorReSend);
                }
                pipeline.addLast(businessHandler);
            }
        };
    }
}
