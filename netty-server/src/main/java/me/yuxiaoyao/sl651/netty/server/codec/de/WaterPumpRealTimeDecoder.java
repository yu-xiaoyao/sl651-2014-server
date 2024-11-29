package me.yuxiaoyao.sl651.netty.server.codec.de;

import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.codec.AbstractObserverTimeFuncDecoder;
import me.yuxiaoyao.sl651.netty.server.element.ElementReaders;
import me.yuxiaoyao.sl651.netty.server.enums.MessageType;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.BaseMessage;
import me.yuxiaoyao.sl651.netty.server.message.WaterPumpRealTimeMessage;
import me.yuxiaoyao.sl651.netty.server.message.item.ElementItem;

/**
 * @author kerryzhang on 2024/11/09
 */

public class WaterPumpRealTimeDecoder extends AbstractObserverTimeFuncDecoder {
    @Override
    public int getFuncCode() {
        // 6.6.4.18 中心站查询水泵电机实时工作数据
        return 0x44;
    }

    @Override
    protected BaseMessage decodeObserverTimeMessage(MessageHeader header, int serialNo, String sendTime, String telemetryId, int telemetryType, String observerTime, ByteBuf buf) {

        var builder = WaterPumpRealTimeMessage.builder()
                .serialNo(serialNo)
                .sendTime(sendTime)
                .telemetryId(telemetryId)
                .telemetryType(telemetryType)
                .observerTime(observerTime);

        var elements = ElementReaders.decodeElements(buf, (flag, ret) -> {
            switch (flag) {
                case 0x70 -> {
                    // 交流A相电压
                    builder.acVoltageA((Double) ret);
                    return null;
                }
                case 0x71 -> {
                    // 交流B相电压
                    builder.acVoltageB((Double) ret);
                    return null;
                }
                case 0x72 -> {
                    // 交流C相电压
                    builder.acVoltageC((Double) ret);
                    return null;
                }
                case 0x73 -> {
                    // 交流A相电流
                    builder.acCurrentA((Double) ret);
                    return null;
                }
                case 0x74 -> {
                    // 交流B相电流
                    builder.acCurrentB((Double) ret);
                    return null;
                }
                case 0x75 -> {
                    // 交流C相电流
                    builder.acCurrentC((Double) ret);
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
