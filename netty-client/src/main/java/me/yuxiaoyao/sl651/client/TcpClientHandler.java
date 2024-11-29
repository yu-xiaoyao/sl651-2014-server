package me.yuxiaoyao.sl651.client;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static me.yuxiaoyao.sl651.client.Sl651ClientApplication.getHex;

/**
 * @author kerryzhang on 2024/11/06
 */

public class TcpClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(TcpClientHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        String hex = ByteBufUtil.hexDump(byteBuf);
        logger.info("收到: {}", hex);


        byteBuf.skipBytes(2);
        byteBuf.skipBytes(5);
        byteBuf.skipBytes(1);
        byteBuf.skipBytes(2); // password
        int funCode = byteBuf.readByte() & 0xFF;


        // TODO test
        if (funCode == 0x37
                || funCode == 0x38
                || funCode == 0x39
                || funCode == 0x44
                || funCode == 0x45
                || funCode == 0x46
                || funCode == 0x47
                || funCode == 0x48
                || funCode == 0x49
                || funCode == 0x4A
                || funCode == 0x50
                || funCode == 0x51) {
            var response = getHex(funCode);
            logger.info("回复. funcCode = {}, size = {}", Integer.toHexString(funCode), response != null ? response.size() : 0);
            if (response != null) {
                for (int i = 0; i < response.size(); i++) {
                    String s = response.get(i);
                    logger.info("回复: {}\t{}", i + 1, s);
                    ctx.writeAndFlush(Unpooled.copiedBuffer(Utils.hexStringToByteArray(s)));
                }
            }
        }
        ReferenceCountUtil.release(msg);
    }
}
