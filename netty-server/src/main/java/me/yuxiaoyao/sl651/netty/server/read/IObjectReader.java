package me.yuxiaoyao.sl651.netty.server.read;


import io.netty.buffer.ByteBuf;

/**
 * @author kerryzhang on 2024/11/20
 */

public interface IObjectReader<T> {
    /**
     * read data to object
     *
     * @param buf
     * @return
     */
    T read(ByteBuf buf);

    default T cast(Object obj) {
        return (T) obj;
    }
}
