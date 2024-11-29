package me.yuxiaoyao.sl651.netty.server.codec.de;

import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.codec.AbstractObserverTimeFuncDecoder;
import me.yuxiaoyao.sl651.netty.server.element.ElementReaders;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.BaseMessage;
import me.yuxiaoyao.sl651.netty.server.message.ElementsMessage;
import me.yuxiaoyao.sl651.netty.server.message.ResponseMessage;
import me.yuxiaoyao.sl651.netty.server.message.item.ElementItem;

/**
 * @author KerryMiBook on 24/11/8
 */


public class QueryElementRealTimeDecoder extends AbstractObserverTimeFuncDecoder {

    @Override
    public int getFuncCode() {
        // 6.6.4.13 中心站查询遥测站指定要素实时数据
        return 0x3A;
    }

    @Override
    protected BaseMessage decodeObserverTimeMessage(MessageHeader header, int serialNo, String sendTime, String telemetryId, int telemetryType, String observerTime, ByteBuf buf) {
        var elements = ElementReaders.decodeElements(buf, (flag, ret) -> new ElementItem(observerTime, flag, ret));
        return ElementsMessage.builder()
                .serialNo(serialNo)
                .sendTime(sendTime)
                .telemetryId(telemetryId)
                .telemetryType(telemetryType)
                .observerTime(observerTime)
                .elements(elements)
                .build();
    }

    @Override
    public ResponseMessage<?> makeResponse(MessageHeader header, BaseMessage message) {
        return null;
    }
}
