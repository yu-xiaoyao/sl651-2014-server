package me.yuxiaoyao.sl651.netty.server.codec.de;

import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.codec.AbstractObserverTimeFuncDecoder;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.BaseMessage;
import me.yuxiaoyao.sl651.netty.server.message.ImageCollectMessage;

/**
 * @author kerryzhang on 2024/11/10
 */

public class ImageCollectFuncDecoder extends AbstractObserverTimeFuncDecoder {

    @Override
    public int getFuncCode() {
        // 6.6.4.9 遥测站图片报或中心站查询遥测站图片采集信息
        return 0x36;
    }

    @Override
    protected BaseMessage decodeObserverTimeMessage(MessageHeader header, int serialNo, String sendTime, String telemetryId, int telemetryType, String observerTime, ByteBuf buf) {
        // F3 F3 skip...
        buf.readShort();
        byte[] image = new byte[buf.readableBytes()];
        buf.readBytes(image);
        return ImageCollectMessage.builder()
                .serialNo(serialNo)
                .sendTime(sendTime)
                .telemetryId(telemetryId)
                .telemetryType(telemetryType)
                .observerTime(observerTime)
                .image(image)
                .build();
    }


    @Override
    public byte[] makeResponseBody(MessageHeader header, BaseMessage message) {
        //TODO
        return super.makeResponseBody(header, message);
    }
}
