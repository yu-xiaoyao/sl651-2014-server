package me.yuxiaoyao.sl651.netty.server.read;


import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.message.item.ConDeviceSnItem;
import me.yuxiaoyao.sl651.netty.server.util.ByteArrayUtil;

/**
 * @author kerryzhang on 2024/11/20
 */

public class DynamicConDeviceSnDataDefine extends AbstractDynamic53LengthReader<ConDeviceSnItem> {

    @Override
    protected ConDeviceSnItem doRead(int dataLen, int decLen, ByteBuf buf) {
        // ASCII
        var cardType = buf.readByte();
        byte[] hex = ByteArrayUtil.readByteBuf(buf, dataLen - 1);
        return new ConDeviceSnItem(new String(new byte[]{cardType}), new String(hex));
    }
}
