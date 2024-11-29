package me.yuxiaoyao.sl651.netty.server.tcp.hex;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.Attribute;
import io.netty.util.ReferenceCountUtil;
import me.yuxiaoyao.sl651.netty.server.codec.BaseFuncDecoder;
import me.yuxiaoyao.sl651.netty.server.codec.Decoders;
import me.yuxiaoyao.sl651.netty.server.enums.ControlChar;
import me.yuxiaoyao.sl651.netty.server.enums.MessageType;
import me.yuxiaoyao.sl651.netty.server.event.DeviceEventPublisher;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.BaseMessage;
import me.yuxiaoyao.sl651.netty.server.message.M3Message;
import me.yuxiaoyao.sl651.netty.server.message.rec.ReceiveMessage;
import me.yuxiaoyao.sl651.netty.server.service.DeviceSendService;
import me.yuxiaoyao.sl651.netty.server.tcp.HexSL651TcpServer;
import me.yuxiaoyao.sl651.netty.server.tcp.TcpSessionManager;
import me.yuxiaoyao.sl651.netty.server.util.CrcUtil;
import me.yuxiaoyao.sl651.netty.server.util.HexUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kerryzhang on 2024/11/06
 */

public class SL651TcpInboundHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SL651TcpInboundHandler.class);
    private final DeviceEventPublisher publisher;
    private final boolean replaceIdleHandler;

    private int readerIdleTimeSeconds = 0;
    private int writerIdleTimeSeconds = 0;
    private int allIdleTimeSeconds = 240;

    /**
     * CRC出错时,是否要求重发
     * false: 表示CRC不正确,也会处理数据
     * true: CRC不正确,拒绝处理数据,回复NAK,要求客户端重新发送
     */
    private boolean crcErrorReSend = false;

    private final Map<Integer, M3Message> m3Packets = new HashMap<>();

    public SL651TcpInboundHandler(DeviceEventPublisher publisher, boolean crcErrorReSend) {
        this.publisher = publisher;
        this.replaceIdleHandler = false;
    }

    public SL651TcpInboundHandler(DeviceEventPublisher publisher, boolean crcErrorReSend, int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds) {
        this.publisher = publisher;
        this.replaceIdleHandler = true;
        this.readerIdleTimeSeconds = readerIdleTimeSeconds;
        this.writerIdleTimeSeconds = writerIdleTimeSeconds;
        this.allIdleTimeSeconds = allIdleTimeSeconds;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.atTrace().log("channelActive: {}", ctx.channel().id());
    }

    private boolean isM3MultiPacket(int startCode) {
        return startCode == ControlChar.SYN;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.atTrace().log("channelInactive: {}", ctx.channel().id());

        Attribute<String> attr = ctx.channel().attr(TcpSessionManager.CHANNEL_GROUP);
        var deviceId = attr.get();
        if (deviceId != null) {
            //新的ChannelId
            ChannelId channelId = TcpSessionManager.get(deviceId);
            //假连接的ChannelId
            if (ctx.channel().id() == channelId) {
                //正常断开...
                ///System.out.println("正常断开");
                TcpSessionManager.remove(deviceId);
                //通知..
                publisher.onDisconnect(deviceId);
            }
        }

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;

        logger.atDebug().log("收到. <- {}", ByteBufUtil.hexDump(in));

        ByteBuf byteBuf = in.readBytes(in.readableBytes() - 2);
        int crc16 = in.readShort() & 0xFFFF;
        ReferenceCountUtil.release(msg);
        boolean correctCrc = crc16 == CrcUtil.calcCrc16(byteBuf);

        // shoule be always 7E 7E
        byte[] frameStart = new byte[2];
        byteBuf.readBytes(frameStart);

        // 0 ~ 255
        var stationId = byteBuf.readByte() & 0xFF;

        // hex
        byte[] telemetryStationArr = new byte[5];
        byteBuf.readBytes(telemetryStationArr);
        var telemetryId = HexUtil.byteArray2HexStr(telemetryStationArr);

        // hex
        byte[] passwordArr = new byte[2];
        byteBuf.readBytes(passwordArr);

        var funcCode = byteBuf.readByte() & 0xFF;

        var dirFlagAndBodyLen = byteBuf.readShort();
        // 高 4 bit
        var dirFlag = (dirFlagAndBodyLen >> 12) & 0xF;   // 总是 0b0000
        var bodyLen = dirFlagAndBodyLen & 0xFFF;

        var packetStartCode = byteBuf.readByte() & 0xFF;

        if (crcErrorReSend && !correctCrc) {
            // TODO reject message and response NAK
            return;
        }

        boolean isM3Mode = isM3MultiPacket(packetStartCode);
        MessageHeader header = MessageHeader.builder()
                .startFrame(frameStart)
                .stationId(stationId)
                .telemetryId(telemetryId)
                .password(HexUtil.byteArray2HexStr(passwordArr))
                .funcCode(funcCode)
                .isM3Mode(isM3Mode)
                .packetStartCode(packetStartCode)
                .build();

        this.attachChannel(ctx, telemetryId);

        ByteBuf bodyBuf;
        if (isM3Mode) {
            // 包总数 / 包序号 3 bytes
            int packetNumber = byteBuf.readMedium();
            int totalPacket = (packetNumber >> 12) & 0xFFF;
            int packetSeq = packetNumber & 0xFFF;
            if (packetSeq == 0) {
                throw new RuntimeException("packetNumber <= 0");
            }

            M3Message m3Message = null;
            if (packetSeq > 1) {
                m3Message = m3Packets.get(funcCode);
            }
            if (m3Message == null) {
                m3Message = new M3Message(header, totalPacket);
                // clear before
                m3Packets.put(funcCode, m3Message);
            }
            byte[] data = new byte[bodyLen - 3];
            byteBuf.readBytes(data);
            m3Message.getPayloads()[packetSeq - 1] = new M3Message.M3Payload(data);

            if (packetSeq < totalPacket) {
                // 没有传输结束, 不需要回复
                ReferenceCountUtil.release(byteBuf);
                return;
            }
            M3Message.M3Payload[] payloads = m3Message.getPayloads();
            for (int i = 0; i < payloads.length; i++) {
                var payload = payloads[i];
                if (payload == null) {
                    //TODO 这个包不完整, 不需要回复,需要发送  NAK ,和包号,重新发送处理
                    return;
                }
            }
            // 获取当前 M3 包 Header
            header = m3Message.getHeader();
            // 合并 M3 包
            bodyBuf = m3Message.getM3Body();
        } else {
            bodyBuf = byteBuf.readSlice(bodyLen);
        }
        header.setPacketEndCode(byteBuf.readByte() & 0xFF);

        var serialNo = bodyBuf.readShort() & 0xFFFF;
        byte[] timeArr = new byte[6];
        bodyBuf.readBytes(timeArr);
        var sendTime = HexUtil.byteArray2HexStr(timeArr);

        var decoder = (BaseFuncDecoder<?>) Decoders.getDecoder(funcCode);
        if (decoder == null) {
            logger.warn("功能码暂时不支持. funcCode = 0x{}, header = {}", Integer.toHexString(funcCode), header);
            ReferenceCountUtil.release(byteBuf);
            return;
        }
        BaseMessage baseMessage = decoder.decodeBody(header, serialNo, sendTime, bodyBuf);

        if (baseMessage == null) {
            ReferenceCountUtil.release(byteBuf);
            logger.warn("decode message is null. header = {}, ", header);
            return;
        }

        if (decoder.messageType() == MessageType.REPORT) {
            publisher.onMessageReport(header, baseMessage);
        } else {
            DeviceSendService.get().onReceiveMessage(new ReceiveMessage<>(header, baseMessage));
        }

        var responseMessage = decoder.makeResponseByObj(header, baseMessage);
        if (responseMessage != null) {
            ctx.writeAndFlush(responseMessage);
        }
        ReferenceCountUtil.release(byteBuf);
    }


    private void attachChannel(ChannelHandlerContext ctx, String deviceId) {

        Attribute<String> attr = ctx.channel().attr(TcpSessionManager.CHANNEL_GROUP);
        var oldDeviceId = attr.setIfAbsent(deviceId);
        TcpSessionManager.TCP_CHANNEL_GROUP.add(ctx.channel());

        if (replaceIdleHandler && oldDeviceId == null) {
            // first receive data
            ctx.pipeline().replace(
                    HexSL651TcpServer.INIT_IDLE_HANDLER_NAME,
                    HexSL651TcpServer.IDLE_HANDLER_NAME,
                    new IdleStateHandler(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds));
        }

        ChannelId currentId = ctx.channel().id();
        ChannelId lastResultChannelId = TcpSessionManager.put(deviceId, currentId);
        if (lastResultChannelId != null && currentId != lastResultChannelId) {
            Channel findChannel = TcpSessionManager.TCP_CHANNEL_GROUP.find(lastResultChannelId);
            if (findChannel != null) {
                logger.warn("ConnectionReset. deviceId = {}", deviceId);
                findChannel.close();
            }
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.ALL_IDLE) {
                ctx.close();
            }
        }
    }
}
