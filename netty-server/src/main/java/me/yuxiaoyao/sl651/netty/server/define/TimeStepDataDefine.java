//package me.yuxiaoyao.sl651.netty.server.define;
//
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import me.yuxiaoyao.sl651.netty.server.message.item.TimeStepValue;
//import me.yuxiaoyao.sl651.netty.server.util.HexUtil;
//
///**
// * @author kerryzhang on 2024/11/19
// */
//
//public class TimeStepDataDefine extends AbstractDynamicLengthDataDefine<TimeStepValue> {
//
//    @Override
//    protected TimeStepValue doRead(int dataLen, int decLen, ByteBuf buf) {
//        assert dataLen == 3;
//        var day = buf.readByte() & 0xFF;
//        var hour = buf.readByte() & 0xFF;
//        var minute = buf.readByte() & 0xFF;
//
//        // 只有一个为 不为 0
//        if (minute > 0) {
//            return TimeStepValue.ofMinute(minute);
//        }
//        if (hour > 0) {
//            return TimeStepValue.ofHour(hour);
//        }
//        return TimeStepValue.ofDay(day);
//    }
//
//    @Override
//    public ByteBuf write(TimeStepValue data) {
//        ByteBuf bodyBuffer = Unpooled.buffer(4);
//        // dataLen = 1
//        bodyBuffer.writeByte(0x18);   // 固定
//        // dataLen = 3
//        bodyBuffer.writeBytes(HexUtil.hexStringToByteArray(data.getTimeStepCode()));
//        return bodyBuffer;
//    }
//}
