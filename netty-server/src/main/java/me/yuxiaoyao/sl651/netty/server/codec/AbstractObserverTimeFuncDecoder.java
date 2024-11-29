package me.yuxiaoyao.sl651.netty.server.codec;

import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.BaseMessage;
import me.yuxiaoyao.sl651.netty.server.util.ByteArrayUtil;
import me.yuxiaoyao.sl651.netty.server.util.HexUtil;

import java.util.Arrays;

/**
 * @author KerryMiBook on 24/11/8
 */


public abstract class AbstractObserverTimeFuncDecoder extends AbstractStationFuncDecoder {

    public static final byte[] OBSERVER_TIME_FLAG = new byte[]{(byte) 0xF0, (byte) 0xF0};


    protected BaseMessage decodeStationMessage(MessageHeader header, int serialNo, String sendTime, String telemetryId, ByteBuf buf) {
        int telemetryType = buf.readByte() & 0xFF;
        byte[] timeFlag = ByteArrayUtil.readByteBuf(buf, 2);

        assert Arrays.equals(OBSERVER_TIME_FLAG, timeFlag);

        String observerTime = HexUtil.byteArray2HexStr(ByteArrayUtil.readByteBuf(buf, 5));
        return decodeObserverTimeMessage(header, serialNo, sendTime, telemetryId, telemetryType, observerTime, buf);
    }


    protected abstract BaseMessage decodeObserverTimeMessage(MessageHeader header, int serialNo, String sendTime, String telemetryId, int telemetryType, String observerTime, ByteBuf buf);

}
