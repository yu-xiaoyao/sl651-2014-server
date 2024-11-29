package me.yuxiaoyao.sl651.netty.server.message;


import io.netty.buffer.ByteBuf;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author kerryzhang on 2024/11/06
 */

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class ByteBufResponseMessage extends ResponseMessage<ByteBuf> {
}
