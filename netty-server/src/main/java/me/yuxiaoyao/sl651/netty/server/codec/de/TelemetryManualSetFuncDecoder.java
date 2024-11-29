package me.yuxiaoyao.sl651.netty.server.codec.de;

import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.codec.AbstractFuncDecoder;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.BaseMessage;
import me.yuxiaoyao.sl651.netty.server.message.DeviceManualSetMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author kerryzhang on 2024/11/06
 */

public class TelemetryManualSetFuncDecoder extends AbstractFuncDecoder {

    private static final Logger logger = LoggerFactory.getLogger(TelemetryManualSetFuncDecoder.class);

    public static final byte[] LEADER_FLAG = new byte[]{(byte) 0xF2, (byte) 0xF2};

    private final Charset charset = StandardCharsets.UTF_8;

    @Override
    public int getFuncCode() {
        // 6.6.4.8 遥测站人工置数报
        return 0x35;
    }

    @Override
    protected BaseMessage decodeFirstLeaderFlag(MessageHeader header, int serialNo, String sendTime, byte[] leaderFlag, ByteBuf buf) {

        // assert Arrays.equals(LEADER_FLAG, leaderFlag);

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String data = new String(bytes, charset);
        return DeviceManualSetMessage.builder()
                .serialNo(serialNo)
                .sendTime(sendTime)
                .content(data)
                .build();
    }
}
