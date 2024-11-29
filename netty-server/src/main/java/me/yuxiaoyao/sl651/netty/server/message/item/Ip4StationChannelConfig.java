package me.yuxiaoyao.sl651.netty.server.message.item;

import lombok.*;

/**
 * @author KerryMiBook on 24/11/9
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ip4StationChannelConfig implements IStationChannelConfig {

    private String ipv4;

    @Override
    public int getChannelType() {
        return 2;
    }

}
