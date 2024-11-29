package me.yuxiaoyao.sl651.netty.server.message;


import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author kerryzhang on 2024/11/11
 */


@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class HourMessage extends ElementsMessage implements IElementMessage {

    private Object every5MinutesHourHyetal;
    /**
     * 降水量累计值
     */
    private Double accumulatedPrecipitation;

    private Object every5MinutesHourWaterLevel;

    /**
     * 电压
     */
    private Double voltage;

}
