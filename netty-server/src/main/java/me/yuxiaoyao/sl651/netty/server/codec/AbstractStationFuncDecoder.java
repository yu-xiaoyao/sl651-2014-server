package me.yuxiaoyao.sl651.netty.server.codec;

import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.exception.FrameInvalidException;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.BaseMessage;
import me.yuxiaoyao.sl651.netty.server.util.ByteArrayUtil;
import me.yuxiaoyao.sl651.netty.server.util.HexUtil;

import java.util.Arrays;

/**
 * @author KerryMiBook on 24/11/8
 */


public abstract class AbstractStationFuncDecoder extends AbstractFuncDecoder {

    public static final byte[] STATION_FLAG = new byte[]{(byte) 0xF1, (byte) 0xF1};


    protected BaseMessage decodeFirstLeaderFlag(MessageHeader header, int serialNo, String sendTime, byte[] leaderFlag, ByteBuf buf) {
        // 遥测站地址, F1 F1
        if (!Arrays.equals(leaderFlag, STATION_FLAG)) {
            throw new FrameInvalidException("遥测站地址标识符不匹配. 收到 = " + HexUtil.byteArray2HexStr(leaderFlag));
        }
        // telemetryId
        var telemetryId = HexUtil.byteArray2HexStr(ByteArrayUtil.readByteBuf(buf, 5));
        return decodeStationMessage(header, serialNo, sendTime, telemetryId, buf);
    }

    protected abstract BaseMessage decodeStationMessage(MessageHeader header, int serialNo, String sendTime, String telemetryId, ByteBuf buf);

}
