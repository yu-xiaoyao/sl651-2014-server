package me.yuxiaoyao.sl651.netty.server.message.item;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kerryzhang on 2024/11/07
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ElementItem {
    private String observerTime;
    private Integer eleId;
    private Object value;
}
