package me.yuxiaoyao.sl651.netty.server.message;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author kerryzhang on 2024/11/06
 */

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseMessage implements IMessage {
    private int serialNo;
    private String sendTime;
}

