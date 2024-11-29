//package me.yuxiaoyao.sl651.netty.server.define;
//
//
//import io.netty.buffer.ByteBuf;
//import me.yuxiaoyao.sl651.netty.server.util.ByteArrayUtil;
//import me.yuxiaoyao.sl651.netty.server.util.HexUtil;
//
///**
// * @author kerryzhang on 2024/11/07
// */
//
//public class BCDDoubleFlagDataDefine extends AbstractDoubleFlagDataDefine<String> {
//
//    private final int dataLen;
//
//    public BCDDoubleFlagDataDefine(int nextFlag, int dataLen) {
//        super(nextFlag);
//        this.dataLen = dataLen;
//    }
//
//
//    @Override
//    protected String doRead(ByteBuf buf) {
//        byte[] bytes = ByteArrayUtil.readByteBuf(buf, this.dataLen);
//        return HexUtil.byteArray2HexStr(bytes);
//    }
//
//    @Override
//    protected byte[] doWrite(String data) {
//        byte[] bytes = HexUtil.hexStringToByteArray(data);
//        assert bytes.length == dataLen;
//        return bytes;
//    }
//
//}
