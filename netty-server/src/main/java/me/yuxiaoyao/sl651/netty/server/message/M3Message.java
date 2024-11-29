package me.yuxiaoyao.sl651.netty.server.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;

/**
 * @author kerryzhang on 2024/11/10
 */

@Data
@SuperBuilder
public class M3Message {
    private MessageHeader header;
    private int total;
    /**
     * seq as index
     */
    private M3Payload[] payloads;


    public M3Message(MessageHeader header, int total) {
        this.header = header;
        this.total = total;
        this.payloads = new M3Payload[total];
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
