//package me.yuxiaoyao.sl651.netty.server.define;
//
//import io.netty.buffer.ByteBuf;
//import me.yuxiaoyao.sl651.netty.server.util.ByteArrayUtil;
//import me.yuxiaoyao.sl651.netty.server.util.HexUtil;
//
///**
// * @author KerryMiBook on 24/11/9
// * <p>
// * TODO 没有数据测试...
// */
//
//
//public class DynamicBcdIntDataDefine extends AbstractDynamicLengthDataDefine<Integer> {
//
//    @Override
//    protected Integer doRead(int dataLen, int decLen, ByteBuf buf) {
//        byte[] bytes = ByteArrayUtil.readByteBuf(buf, dataLen);
//        return Integer.parseInt(HexUtil.byteArray2HexStr(bytes), 16);
//    }
//
//    @Override
//    public ByteBuf write(Integer data) {
//        //
//        return null;
//    }
//}
