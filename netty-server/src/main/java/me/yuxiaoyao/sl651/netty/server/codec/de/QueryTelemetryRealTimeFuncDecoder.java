package me.yuxiaoyao.sl651.netty.server.codec.de;


import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.codec.AbstractObserverTimeFuncDecoder;
import me.yuxiaoyao.sl651.netty.server.element.ElementReaders;
import me.yuxiaoyao.sl651.netty.server.enums.MessageType;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.BaseMessage;
import me.yuxiaoyao.sl651.netty.server.message.RealTimeMessage;
import me.yuxiaoyao.sl651.netty.server.message.item.ElementItem;

/**
 * @author kerryzhang on 2024/11/07
 */

public class QueryTelemetryRealTimeFuncDecoder extends AbstractObserverTimeFuncDecoder {
    @Override
    public int getFuncCode() {
        // 6.6.4.10 中心站查询遥测站实时数据
        return 0x37;
    }

    @Override
    protected BaseMessage decodeObserverTimeMessage(MessageHeader header, int serialNo, String sendTime, String telemetryId, int telemetryType, String observerTime, ByteBuf buf) {

        var builder = RealTimeMessage.builder()
                .serialNo(serialNo)
                .sendTime(sendTime)
                .telemetryId(telemetryId)
                .telemetryType(telemetryType)
                .observerTime(observerTime);

        var elements = ElementReaders.decodeElements(buf, (flag, ret) -> {
            switch (flag) {
                case 0x20 -> {
                    // 当前降水量
                    builder.currentPrecipitation((Double) ret);
                    return null;
                }
                case 0x26 -> {
                    // 降水量累计值
                    builder.accumulatedPrecipitation((Double) ret);
                    return null;
                }
                case 0x39 -> {
                    // 瞬时水位
                    builder.instantaneousWaterLevel((Double) ret);
                    return null;
                }
                case 0x38 -> {
                    // 电压
                    builder.voltage((Double) ret);
                    return null;
                }
            }
            return new ElementItem(observerTime, flag, ret);
        });
        return builder.elements(elements).build();
    }

    @Override
    public MessageType messageType() {
        return MessageType.READ;
    }
}
