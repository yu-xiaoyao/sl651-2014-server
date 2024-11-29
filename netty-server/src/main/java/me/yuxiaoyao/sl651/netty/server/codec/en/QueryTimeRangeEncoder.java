package me.yuxiaoyao.sl651.netty.server.codec.en;


import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.codec.BaseFuncEncoder;
import me.yuxiaoyao.sl651.netty.server.message.send.QueryTimeRangeSendMessage;
import me.yuxiaoyao.sl651.netty.server.util.DateTimeUtil;
import me.yuxiaoyao.sl651.netty.server.util.HexUtil;

/**
 * @author kerryzhang on 2024/11/19
 */

public class QueryTimeRangeEncoder implements BaseFuncEncoder<QueryTimeRangeSendMessage> {

    public static QueryTimeRangeEncoder INSTANCE = new QueryTimeRangeEncoder();

    @Override
    public void encodeBody(ByteBuf bodyBuffer, QueryTimeRangeSendMessage sendMessage) {
        bodyBuffer.writeBytes(HexUtil.hexStringToByteArray(DateTimeUtil.formatTimeRange(sendMessage.getStartTime())));
        bodyBuffer.writeBytes(HexUtil.hexStringToByteArray(DateTimeUtil.formatTimeRange(sendMessage.getEndTime())));

        // 时长步长码
        bodyBuffer.writeByte(0x04);
        bodyBuffer.writeByte(0x18);   // 固定
        bodyBuffer.writeBytes(HexUtil.hexStringToByteArray(sendMessage.getTimeStep().getTimeStepCode()));
        // end time step code

        // 要素标识符
        bodyBuffer.writeByte(sendMessage.getEleIdentifier());
    }
}
