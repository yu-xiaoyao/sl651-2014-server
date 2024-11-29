package me.yuxiaoyao.sl651.netty.server.message.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kerryzhang on 2024/11/09
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfigMessageItem {
    private int configFlag;
    private Object value;
}
