package me.yuxiaoyao.sl651.netty.server.read;


import io.netty.buffer.ByteBuf;
import lombok.Getter;
import me.yuxiaoyao.sl651.netty.server.util.ByteArrayUtil;
import me.yuxiaoyao.sl651.netty.server.util.HexUtil;

/**
 * @author kerryzhang on 2024/11/20
 */

@Getter
public class BcdRepeatFlagReader extends AbstractRepeatFlagReader<String> {
    private final int dataLen;

    public BcdRepeatFlagReader(int repeatFlag, int dataLen) {
        super(repeatFlag);
        this.dataLen = dataLen;
    }

    @Override
    protected String doRead(ByteBuf buf) {
        byte[] bytes = ByteArrayUtil.readByteBuf(buf, this.dataLen);
        return HexUtil.byteArray2HexStr(bytes);
    }
}
