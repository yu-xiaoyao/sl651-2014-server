//package me.yuxiaoyao.sl651.netty.server.tcp.hex;
//
//import com.google.common.collect.HashMultimap;
//import com.google.common.collect.Multimap;
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.ByteToMessageDecoder;
//import me.yuxiaoyao.sl651.netty.server.enums.ControlChar;
//import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
//import me.yuxiaoyao.sl651.netty.server.message.ByteBufResponseMessage;
//import me.yuxiaoyao.sl651.netty.server.util.HexUtil;
//
//import java.util.Collection;
//import java.util.List;
//
///**
// * @author kerryzhang on 2024/11/10
// */
//
//public class SL651TcpPreMessageDecoder extends ByteToMessageDecoder {
//
//    private final Multimap<Integer, ByteBufResponseMessage> m3Packets = HashMultimap.create();
//
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        //Table<Integer, Integer, List<ByteBufResponseMessage>> m3Packets = this.m3Packets;
//
//        ctx.fireChannelInactive();
//    }
//
//    @Override
//    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
//        // is is full frame
//
//
//        byte[] frameStart = new byte[2];
//        in.readBytes(frameStart);
//
//        // 0 ~ 255
//        var stationId = in.readByte() & 0xFF;
//
//        // hex
//        byte[] telemetryStationArr = new byte[5];
//        in.readBytes(telemetryStationArr);
//        var telemetryId = HexUtil.byteArray2HexStr(telemetryStationArr);
//
//        // hex
//        byte[] passwordArr = new byte[2];
//        in.readBytes(passwordArr);
//
//        var funcCode = in.readByte() & 0xFF;
//
//        var dirFlagAndBodyLen = in.readShort();
//        // 高 4 bit
//        var dirFlag = (dirFlagAndBodyLen >> 12) & 0xF;   // 总是 0b0000
//        var bodyLen = dirFlagAndBodyLen & 0xFFF;
//
//        var packetStartCode = in.readByte() & 0xFF;
//
//        ByteBuf body = in.readSlice(bodyLen);
//        var packetEndCode = in.readByte() & 0xFF;
//
//        // CRC 16
//        in.skipBytes(2);
//
//        var header = MessageHeader.builder()
//                .startFrame(frameStart)
//                .stationId(stationId)
//                .telemetryId(telemetryId)
//                .password(HexUtil.byteArray2HexStr(passwordArr))
//                .funcCode(funcCode)
//                .packetStartCode(packetStartCode)
//                .packetEndCode(packetEndCode)
//                .build();
//
//
//        if (packetStartCode == ControlChar.SYN) {
//            // M3
//            // 包总数 / 包序号 3 bytes
//            int packetNumber = in.readMedium();
//            // 提取前 12 位
//            int totalPacket = (packetNumber >> 12) & 0xFFF;
//            // 提取后 12 位
//            int packetSeq = packetNumber & 0xFFF;
//            if (totalPacket >= packetSeq) {
//                // 传输完成
//                Collection<ByteBufResponseMessage> msgs = m3Packets.removeAll(funcCode);
//                // TODO
//            } else {
//                // 一般省
//                ByteBufResponseMessage msg = ByteBufResponseMessage.builder().header(header).body(body).build();
//                m3Packets.get(funcCode).add(msg);
//                out.add(msg);
//            }
//        } else {
//            var msg = ByteBufResponseMessage.builder().header(header).body(body).build();
//            out.add(msg);
//        }
//    }
//}
