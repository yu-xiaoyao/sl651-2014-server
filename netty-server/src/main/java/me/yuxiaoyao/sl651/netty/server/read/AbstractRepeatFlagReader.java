package me.yuxiaoyao.sl651.netty.server.read;


import io.netty.buffer.ByteBuf;
import lombok.Getter;

/**
 * @author kerryzhang on 2024/11/20
 */

@Getter
public abstract class AbstractRepeatFlagReader<T> implements IObjectReader<T> {

    protected final int repeatFlag;

    public AbstractRepeatFlagReader(int repeatFlag) {
        this.repeatFlag = repeatFlag;
    }

    public T read(ByteBuf buf) {
        int fixFlag = buf.readByte() & 0xFF;
        assert repeatFlag == fixFlag;
        return doRead(buf);
    }

    protected abstract T doRead(ByteBuf buf);
}
