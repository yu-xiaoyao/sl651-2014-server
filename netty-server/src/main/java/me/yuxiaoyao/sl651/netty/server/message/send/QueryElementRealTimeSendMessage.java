package me.yuxiaoyao.sl651.netty.server.message.send;


import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author kerryzhang on 2024/11/18
 */

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class QueryElementRealTimeSendMessage extends BaseSendMessage {
    private int[] elements;
}
