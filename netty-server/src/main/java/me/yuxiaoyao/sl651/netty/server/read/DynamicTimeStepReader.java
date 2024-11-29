package me.yuxiaoyao.sl651.netty.server.read;


import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.message.item.TimeStepValue;

/**
 * @author kerryzhang on 2024/11/20
 */

public class DynamicTimeStepReader extends AbstractDynamic53LengthReader<TimeStepValue> {

    @Override
    protected TimeStepValue doRead(int dataLen, int decLen, ByteBuf buf) {
        assert dataLen == 3;
        var day = buf.readByte() & 0xFF;
        var hour = buf.readByte() & 0xFF;
        var minute = buf.readByte() & 0xFF;

        // 只有一个为 不为 0
        if (minute > 0) {
            return TimeStepValue.ofMinute(minute);
        }
        if (hour > 0) {
            return TimeStepValue.ofHour(hour);
        }
        return TimeStepValue.ofDay(day);
    }
}
