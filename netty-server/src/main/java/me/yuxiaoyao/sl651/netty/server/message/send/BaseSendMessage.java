package me.yuxiaoyao.sl651.netty.server.message.send;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;

/**
 * @author kerryzhang on 2024/11/18
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class BaseSendMessage implements ISendMessage {
    private MessageHeader header;
    private String sendTime;
    private int serialNo;
}
