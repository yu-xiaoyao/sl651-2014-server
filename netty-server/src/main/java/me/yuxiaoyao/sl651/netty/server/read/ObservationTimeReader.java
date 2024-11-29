package me.yuxiaoyao.sl651.netty.server.read;


/**
 * @author kerryzhang on 2024/11/20
 */

public class ObservationTimeReader extends BcdRepeatFlagReader {
    public ObservationTimeReader() {
        super(0xF0, 5);
    }
}
