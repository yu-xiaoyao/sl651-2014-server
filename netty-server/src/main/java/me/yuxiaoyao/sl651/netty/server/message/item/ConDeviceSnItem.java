package me.yuxiaoyao.sl651.netty.server.message.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author KerryMiBook on 24/11/9
 */


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConDeviceSnItem {
    private String type;
    private String sn;
}
