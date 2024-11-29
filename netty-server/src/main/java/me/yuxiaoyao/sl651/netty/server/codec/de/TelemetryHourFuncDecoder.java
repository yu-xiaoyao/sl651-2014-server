package me.yuxiaoyao.sl651.netty.server.codec.de;

import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.codec.AbstractObserverTimeFuncDecoder;
import me.yuxiaoyao.sl651.netty.server.element.ElementReaders;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.BaseMessage;
import me.yuxiaoyao.sl651.netty.server.message.HourMessage;
import me.yuxiaoyao.sl651.netty.server.message.item.ElementItem;

/**
 * @author kerryzhang on 2024/11/06
 */

public class TelemetryHourFuncDecoder extends AbstractObserverTimeFuncDecoder {

    @Override
    public int getFuncCode() {
        // 遥测站小时报
        return 0x34;
    }

    @Override
    protected BaseMessage decodeObserverTimeMessage(MessageHeader header, int serialNo, String sendTime, String telemetryId, int telemetryType, String observerTime, ByteBuf buf) {
        var builder = HourMessage.builder()
                .serialNo(serialNo)
                .sendTime(sendTime)
                .telemetryId(telemetryId)
                .telemetryType(telemetryType)
                .observerTime(observerTime);

        var elements = ElementReaders.decodeElements(buf, (flag, ret) -> {
            switch (flag) {
                case 0xF4 -> {
                    // 1小时内每5分钟时段降水量
                    builder.every5MinutesHourHyetal(ret);
                    return null;
                }
                case 0xF5 -> {
                    // 1 小时内 5 分钟间隔相对水位
                    builder.every5MinutesHourWaterLevel(ret);
                    return null;
                }
                case 0x26 -> {
                    // 降水量累计值
                    builder.accumulatedPrecipitation((Double) ret);
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
}
