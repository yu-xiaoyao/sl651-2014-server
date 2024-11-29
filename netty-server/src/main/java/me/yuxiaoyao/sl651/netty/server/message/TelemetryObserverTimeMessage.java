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
public class TelemetryObserverTimeMessage extends TelemetryIdTypeMessage {
    private String observerTime;
}
