package me.yuxiaoyao.sl651.netty.server.message;

import lombok.*;
import lombok.experimental.SuperBuilder;
import me.yuxiaoyao.sl651.netty.server.message.common.ConfigMessageItem;

import java.util.List;

/**
 * @author KerryMiBook on 24/11/8
 */

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TelemetryConfigMessage extends TelemetryIdMessage {
    private List<ConfigMessageItem> configs;
}
