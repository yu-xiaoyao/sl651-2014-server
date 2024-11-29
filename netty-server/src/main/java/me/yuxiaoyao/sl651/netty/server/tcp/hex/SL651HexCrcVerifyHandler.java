//package me.yuxiaoyao.sl651.netty.server.tcp.hex;
//
//
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundHandlerAdapter;
//import io.netty.util.ReferenceCountUtil;
//import me.yuxiaoyao.sl651.netty.server.util.CrcUtil;
//import me.yuxiaoyao.sl651.netty.server.util.HexUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Arrays;
//
///**
// * @author kerryzhang on 2024/11/06
// */
//
//public class SL651HexCrcVerifyHandler extends ChannelInboundHandlerAdapter {
//
//    private static final Logger logger = LoggerFactory.getLogger(SL651HexCrcVerifyHandler.class);
//
//    private final boolean forceVerifyCrc;
//
//    public SL651HexCrcVerifyHandler(boolean forceVerifyCrc) {
//        this.forceVerifyCrc = forceVerifyCrc;
//    }
//
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf byteBuf = (ByteBuf) msg;
//        byteBuf.markReaderIndex();
//        int bodyLen = byteBuf.readableBytes() - 2;
//        byte[] body = new byte[bodyLen];
//        byteBuf.readBytes(body);
//
//        byte[] recCrc = new byte[2];
//        byteBuf.readBytes(recCrc);
//
//        logger.atDebug().log("接收. <-- {}{}", HexUtil.byteArray2HexStr(body), HexUtil.byteArray2HexStr(recCrc));
//
//        if (forceVerifyCrc) {
//            byte[] calcCrc = CrcUtil.calcCrc16Bytes(body);
//            if (Arrays.equals(calcCrc, recCrc)) {
//                byteBuf.resetReaderIndex();
//                ctx.fireChannelRead(msg);
//            } else {
//                logger.warn("CalcCrc16 error. crc = {}, calcCrc16 = {}", HexUtil.byteArray2HexStr(recCrc), HexUtil.byteArray2HexStr(calcCrc));
//                ReferenceCountUtil.release(msg);
//                //TODO CRC 16 检验码不正确,是否断开??
//                ctx.close();
//            }
//        } else {
//            byteBuf.resetReaderIndex();
//            ctx.fireChannelRead(msg);
//        }
//    }
//
//}
