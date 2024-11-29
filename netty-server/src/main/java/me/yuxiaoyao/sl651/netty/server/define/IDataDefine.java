//package me.yuxiaoyao.sl651.netty.server.define;
//
//import io.netty.buffer.ByteBuf;
//
///**
// * @author kerryzhang on 2024/11/07
// */
//public interface IDataDefine<T> {
//
//    /**
//     * read data to object
//     *
//     * @param buf
//     * @return
//     */
//    T read(ByteBuf buf);
//
//    /**
//     * write object to ByteBuf
//     *
//     * @param data
//     * @return
//     */
//    ByteBuf write(T data);
//
//    default T cast(Object obj) {
//        return (T) obj;
//    }
//
//
//}
