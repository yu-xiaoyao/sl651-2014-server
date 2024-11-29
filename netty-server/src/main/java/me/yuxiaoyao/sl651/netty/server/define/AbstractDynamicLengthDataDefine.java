//package me.yuxiaoyao.sl651.netty.server.define;
//
//import io.netty.buffer.ByteBuf;
//
///**
// * @author KerryMiBook on 24/11/9
// */
//
//
//public abstract class AbstractDynamicLengthDataDefine<T> implements IDataDefine<T> {
//
//    @Override
//    public T read(ByteBuf buf) {
//        byte len = buf.readByte();
//        // 数据长度. 提取高 5 位 // 0x1F 为 5 位掩码（0001 1111）
//        int dataLen = (len >> 3) & 0x1F;
//        // 小数长度. 提取低 3 位 // 0x07 为 3 位掩码（0000 0111）
//        int decLen = len & 0x07;
//
//        // return doResolveDefine(dataLen, decLen, buf.readSlice(dataLen));
//        return doRead(dataLen, decLen, buf);
//    }
//
//    protected abstract T doRead(int dataLen, int decLen, ByteBuf buf);
//
//
//    protected int mergeDataLen(int dataLen, int decLen) {
//        return (dataLen << 3) | (decLen & 0x07);
//    }
//}
