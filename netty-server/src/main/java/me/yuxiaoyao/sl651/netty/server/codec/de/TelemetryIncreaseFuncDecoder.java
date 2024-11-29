package me.yuxiaoyao.sl651.netty.server.codec.de;

import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.codec.AbstractObserverTimeFuncDecoder;
import me.yuxiaoyao.sl651.netty.server.element.ElementReaders;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.BaseMessage;
import me.yuxiaoyao.sl651.netty.server.message.TelemetryIncreaseMessage;
import me.yuxiaoyao.sl651.netty.server.message.item.ElementItem;

/**
 * @author kerryzhang on 2024/11/06
 */

public class TelemetryIncreaseFuncDecoder extends AbstractObserverTimeFuncDecoder {

    @Override
    public int getFuncCode() {
        // 遥测站加报报
        return 0x33;
    }

    @Override
    protected BaseMessage decodeObserverTimeMessage(MessageHeader header, int serialNo, String sendTime, String telemetryId, int telemetryType, String observerTime, ByteBuf buf) {
        var builder = TelemetryIncreaseMessage.builder()
                .serialNo(serialNo)
                .sendTime(sendTime)
                .telemetryId(telemetryId)
                .telemetryType(telemetryType)
                .observerTime(observerTime);

        var elements = ElementReaders.decodeElements(buf, (flag, ret) -> {
            if (flag == 0x38) {
                // 电压
                builder.voltage((Double) ret);
                return null;
            }
            //TODO 触发要素??

            return new ElementItem(observerTime, flag, ret);
        });

        return builder.elements(elements).build();
    }
}
