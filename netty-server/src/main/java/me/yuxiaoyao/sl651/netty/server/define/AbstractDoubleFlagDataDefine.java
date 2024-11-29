//package me.yuxiaoyao.sl651.netty.server.define;
//
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import lombok.Setter;
//
///**
// * @author kerryzhang on 2024/11/07
// */
//
//@Setter
//public abstract class AbstractDoubleFlagDataDefine<T> implements IDataDefine<T> {
//
//    protected final int nextFlag;
//
//    public AbstractDoubleFlagDataDefine(int nextFlag) {
//        this.nextFlag = nextFlag;
//    }
//
//    public T read(ByteBuf buf) {
//        int fixFlag = buf.readByte() & 0xFF;
//        assert nextFlag == fixFlag;
//        return doRead(buf);
//    }
//
//    protected abstract T doRead(ByteBuf buf);
//
//    @Override
//    public ByteBuf write(T data) {
//        ByteBuf buffer = Unpooled.buffer();
//        buffer.writeByte(nextFlag);
//        var body = doWrite(data);
//        buffer.writeBytes(body);
//        return buffer;
//    }
//
//    protected abstract byte[] doWrite(T data);
//}
