package me.yuxiaoyao.sl651.netty.server.message;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author kerryzhang on 2024/11/09
 */

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TelemetryIdMessage extends BaseMessage {
    /**
     * 遥测站地址
     */
    private String telemetryId;
}
