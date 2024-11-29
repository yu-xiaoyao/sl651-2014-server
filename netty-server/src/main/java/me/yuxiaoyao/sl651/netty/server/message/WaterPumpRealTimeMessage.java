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
public class WaterPumpRealTimeMessage extends ElementsMessage implements IElementMessage {
    /**
     * 交流A相电压
     */
    private Double acVoltageA;
    /**
     * 交流B相电压
     */
    private Double acVoltageB;
    /**
     * 交流C相电压
     */
    private Double acVoltageC;
    /**
     * 交流A相电流
     */
    private Double acCurrentA;
    /**
     * 交流B相电流
     */
    private Double acCurrentB;
    /**
     * 交流C相电流
     */
    private Double acCurrentC;
}
