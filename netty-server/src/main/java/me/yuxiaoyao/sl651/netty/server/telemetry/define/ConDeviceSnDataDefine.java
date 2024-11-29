//package me.yuxiaoyao.sl651.netty.server.telemetry.define;
//
//import io.netty.buffer.ByteBuf;
//import me.yuxiaoyao.sl651.netty.server.define.AbstractDynamicLengthDataDefine;
//import me.yuxiaoyao.sl651.netty.server.message.item.ConDeviceSnItem;
//import me.yuxiaoyao.sl651.netty.server.util.ByteArrayUtil;
//
///**
// * @author KerryMiBook on 24/11/9
// */
//
//
//public class ConDeviceSnDataDefine extends AbstractDynamicLengthDataDefine<ConDeviceSnItem> {
//    @Override
//    protected ConDeviceSnItem doRead(int dataLen, int decLen, ByteBuf buf) {
//        // ASCII
//        var cardType = buf.readByte();
//        byte[] hex = ByteArrayUtil.readByteBuf(buf, dataLen - 1);
//        return new ConDeviceSnItem(new String(new byte[]{cardType}), new String(hex));
//    }
//
//    @Override
//    public ByteBuf write(ConDeviceSnItem data) {
//        // only read
//        return null;
//    }
//}
