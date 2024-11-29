//package me.yuxiaoyao.sl651.netty.server.define;
//
//import io.netty.buffer.ByteBuf;
//import me.yuxiaoyao.sl651.netty.server.util.ByteArrayUtil;
//import me.yuxiaoyao.sl651.netty.server.util.HexUtil;
//
///**
// * @author KerryMiBook on 24/11/9
// */
//
//
//public class DynamicHexLengthDataDefine extends AbstractDynamicLengthDataDefine<String> {
//    @Override
//    protected String doRead(int dataLen, int decLen, ByteBuf buf) {
//        return HexUtil.byteArray2HexStr(ByteArrayUtil.readByteBuf(buf, dataLen));
//    }
//
//    @Override
//    public ByteBuf write(String data) {
//        return null;
//    }
//}
