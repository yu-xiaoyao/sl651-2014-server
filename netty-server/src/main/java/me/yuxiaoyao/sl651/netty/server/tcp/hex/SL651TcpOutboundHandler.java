package me.yuxiaoyao.sl651.netty.server.tcp.hex;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.ByteBufResponseMessage;
import me.yuxiaoyao.sl651.netty.server.util.CrcUtil;
import me.yuxiaoyao.sl651.netty.server.util.HexUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kerryzhang on 2024/11/06
 */

public class SL651TcpOutboundHandler extends ChannelOutboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SL651TcpOutboundHandler.class);


    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof ByteBufResponseMessage brm) {
            MessageHeader header = brm.getHeader();
            ByteBuf body = brm.getBody();
            ByteBuf buffer = Unpooled.buffer();
            buffer.writeBytes(header.getStartFrame());
            buffer.writeBytes(HexUtil.hexStringToByteArray(header.getTelemetryId()));
            buffer.writeByte(header.getStationId());
            buffer.writeBytes(HexUtil.hexStringToByteArray(header.getPassword()));
            buffer.writeByte(header.getFuncCode());

            int flagAndBodyLen = body.readableBytes();
            final int flag = 0b1000;
            int bodyLen = flagAndBodyLen & 0xFFF;
            var len = ((flag << 12) | bodyLen);
            buffer.writeShort(len);

            buffer.writeByte(header.getPacketStartCode());
            if (bodyLen > 0) {
                buffer.writeBytes(body);
                ReferenceCountUtil.release(body);
            }
            buffer.writeByte(brm.getResponsePacketEndCode());

            byte[] bytes = new byte[buffer.readableBytes()];
            buffer.readBytes(bytes);
            ReferenceCountUtil.refCnt(buffer);

            byte[] crc16 = CrcUtil.calcCrc16Bytes(bytes);
            ByteBuf sendBuf = Unpooled.copiedBuffer(bytes, crc16);

            logger.atDebug().log("发送. -> telemetryStationId: {}, {}", header.getTelemetryId(), ByteBufUtil.hexDump(sendBuf));

            super.write(ctx, sendBuf, promise);
        } else if (msg instanceof ByteBuf buf) {
            logger.atDebug().log("发送ByteBuf. -> {}", ByteBufUtil.hexDump(buf));
            super.write(ctx, msg, promise);
        } else {
            logger.warn("发送. -> 未知消息. type = {}", msg.getClass());
            super.write(ctx, msg, promise);
        }
    }
}
