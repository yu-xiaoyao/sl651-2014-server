package me.yuxiaoyao.sl651.netty.server.message;


import lombok.*;
import lombok.experimental.SuperBuilder;
import me.yuxiaoyao.sl651.netty.server.message.item.ElementItem;

import java.util.List;

/**
 * @author kerryzhang on 2024/11/11
 */


@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TelemetryIncreaseMessage extends TelemetryObserverTimeMessage implements IElementMessage {
    /**
     * 电压
     */
    private Double voltage;

    /**
     * 其他要素
     */
    private List<ElementItem> elements;

}
