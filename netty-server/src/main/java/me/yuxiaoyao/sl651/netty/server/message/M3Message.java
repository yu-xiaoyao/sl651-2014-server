package me.yuxiaoyao.sl651.netty.server.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author kerryzhang on 2024/11/10
 */

@Data
@SuperBuilder
public class M3Message {

    private int total;
    /**
     * seq as index
     */
    private M3Payload[] payloads;


    public M3Message(int total) {
        this.total = total;
        this.payloads = new M3Payload[this.total];
    }

    public ByteBuf getM3Body() {
        ByteBuf buf = Unpooled.buffer();
        for (M3Payload payload : payloads) {
            buf.writeBytes(payload.data);
        }
        return buf;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class M3Payload {
        private byte[] data;
    }
}
