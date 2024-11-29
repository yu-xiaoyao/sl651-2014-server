package me.yuxiaoyao.sl651.netty.server.message.rec;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.BaseMessage;

/**
 * @author kerryzhang on 2024/11/18
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReceiveMessage<T extends BaseMessage> {
    private MessageHeader header;
    private T body;
}
