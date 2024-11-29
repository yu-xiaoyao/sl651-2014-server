package me.yuxiaoyao.sl651.netty.server.message;


import lombok.*;
import lombok.experimental.SuperBuilder;
import me.yuxiaoyao.sl651.netty.server.message.item.ElementItem;
import me.yuxiaoyao.sl651.netty.server.message.item.TimeStepValue;

import java.util.List;

/**
 * @author kerryzhang on 2024/11/06
 */

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TelemetryTimeStepMessage extends TelemetryObserverTimeMessage implements IElementMessage {
    /**
     * 时间步长码
     */
    private TimeStepValue timeStepCode;

    /**
     * 其他要素
     */
    private List<ElementItem> elements;


}
