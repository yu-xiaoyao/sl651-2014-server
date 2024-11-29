package me.yuxiaoyao.sl651.netty.server.message.send;


import lombok.*;
import lombok.experimental.SuperBuilder;
import me.yuxiaoyao.sl651.netty.server.message.item.TimeStepValue;

import java.time.LocalDateTime;

/**
 * @author kerryzhang on 2024/11/19
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class QueryTimeRangeSendMessage extends BaseSendMessage {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private TimeStepValue timeStep;
    /**
     * 要素标识符,目前只支持一个
     */
    private int eleIdentifier;
}
