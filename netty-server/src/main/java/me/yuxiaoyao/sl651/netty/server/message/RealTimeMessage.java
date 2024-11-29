package me.yuxiaoyao.sl651.netty.server.message;


import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author kerryzhang on 2024/11/06
 */

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RealTimeMessage extends ElementsMessage implements IElementMessage {
    /**
     * 当前降水量
     */
    private Double currentPrecipitation;
    /**
     * 降水量累计值
     */
    private Double accumulatedPrecipitation;
    /**
     * 瞬时水位
     */
    private Double instantaneousWaterLevel;

    /**
     * 电压
     */
    private Double voltage;
}
