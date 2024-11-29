package me.yuxiaoyao.sl651.netty.server.read;


import io.netty.buffer.ByteBuf;

/**
 * @author kerryzhang on 2024/11/20
 */

public class TimeHHmmReader implements IObjectReader<String> {

    public static TimeHHmmReader INSTANCE = new TimeHHmmReader();

    @Override
    public String read(ByteBuf buf) {
        return "";
    }
}
