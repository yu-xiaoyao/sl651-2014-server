package me.yuxiaoyao.sl651.netty.server.read;


import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.util.ByteArrayUtil;
import me.yuxiaoyao.sl651.netty.server.util.HexUtil;

/**
 * @author kerryzhang on 2024/11/20
 */

public class DynamicBcdIntReader extends AbstractDynamic53LengthReader<Integer> {
    @Override
    protected Integer doRead(int dataLen, int decLen, ByteBuf buf) {
        byte[] bytes = ByteArrayUtil.readByteBuf(buf, dataLen);
        return Integer.parseInt(HexUtil.byteArray2HexStr(bytes), 16);
    }
}
