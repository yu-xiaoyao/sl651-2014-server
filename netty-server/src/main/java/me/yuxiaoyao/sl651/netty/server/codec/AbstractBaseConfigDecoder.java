package me.yuxiaoyao.sl651.netty.server.codec;


import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.telemetry.TelemetryBaseConfigTable;

import java.util.function.BiConsumer;

/**
 * @author kerryzhang on 2024/11/19
 */

public abstract class AbstractBaseConfigDecoder extends AbstractStationFuncDecoder {

    protected void handleBaseConfig(ByteBuf buf, BiConsumer<Integer, Object> consumer) {
        while (buf.isReadable()) {
            var flag = buf.readByte() & 0xFF;
            var reader = TelemetryBaseConfigTable.getObjectReader(flag);
            if (reader == null) {
                throw new RuntimeException("未知基本配置. 请检查 TelemetryBaseConfigTable 中的对应关系是否缺少. flag: 0x" + Integer.toHexString(flag));
            }
            Object value = reader.read(buf);
            consumer.accept(flag, value);
        }
    }
}
