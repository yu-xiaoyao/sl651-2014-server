package me.yuxiaoyao.sl651.netty.server.read;


import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.util.ByteArrayUtil;
import me.yuxiaoyao.sl651.netty.server.util.HexUtil;

/**
 * @author kerryzhang on 2024/11/20
 */

public class DynamicHexStringReader extends AbstractDynamic53LengthReader<String> {
    @Override
    protected String doRead(int dataLen, int decLen, ByteBuf buf) {
        return HexUtil.byteArray2HexStr(ByteArrayUtil.readByteBuf(buf, dataLen));
    }
}
