package me.yuxiaoyao.sl651.netty.server.message;


import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author kerryzhang on 2024/11/07
 */


@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TelemetryIdTypeMessage extends TelemetryIdMessage {
    /**
     * 遥测站分类码
     */
    private int telemetryType;
}
