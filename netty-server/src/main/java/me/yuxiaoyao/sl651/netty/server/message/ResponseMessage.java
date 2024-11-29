package me.yuxiaoyao.sl651.netty.server.message;


import lombok.Data;
import lombok.experimental.SuperBuilder;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;

/**
 * @author kerryzhang on 2024/11/06
 */

@Data
@SuperBuilder
public class ResponseMessage<T> {
    private MessageHeader header;
    private T body;
    private int responsePacketEndCode;
}
