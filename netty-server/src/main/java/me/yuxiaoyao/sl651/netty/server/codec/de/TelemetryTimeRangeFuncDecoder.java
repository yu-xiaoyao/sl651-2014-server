package me.yuxiaoyao.sl651.netty.server.codec.de;

import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.codec.AbstractObserverTimeFuncDecoder;
import me.yuxiaoyao.sl651.netty.server.element.ElementReaders;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.BaseMessage;
import me.yuxiaoyao.sl651.netty.server.message.TelemetryTimeStepMessage;
import me.yuxiaoyao.sl651.netty.server.message.item.ElementItem;
import me.yuxiaoyao.sl651.netty.server.message.item.TimeStepValue;

/**
 * @author kerryzhang on 2024/11/06
 */

public class TelemetryTimeRangeFuncDecoder extends AbstractObserverTimeFuncDecoder {

    @Override
    public int getFuncCode() {
        // 6.6.4.4 均匀时段水文信息报
        return 0x31;
    }


    @Override
    protected BaseMessage decodeObserverTimeMessage(MessageHeader header, int serialNo, String sendTime,
                                                    String telemetryId, int telemetryType, String observerTime, ByteBuf buf) {
        var builder = TelemetryTimeStepMessage.builder()
                .serialNo(serialNo)
                .sendTime(sendTime)
                .telemetryId(telemetryId)
                .telemetryType(telemetryType)
                .observerTime(observerTime);

        var elements = ElementReaders.decodeElements(buf, (flag, ret) -> {
            // 时间步长码
            if (flag == 0x04) {
                builder.timeStepCode((TimeStepValue) ret);
                return null;
            }
            return new ElementItem(observerTime, flag, ret);
        });

        return builder.elements(elements).build();
    }

}
