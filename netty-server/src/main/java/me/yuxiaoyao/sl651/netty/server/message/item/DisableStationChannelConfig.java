package me.yuxiaoyao.sl651.netty.server.message.item;

import lombok.Data;

/**
 * @author KerryMiBook on 24/11/9
 */


@Data
public class DisableStationChannelConfig implements IStationChannelConfig {

    @Override
    public int getChannelType() {
        return 0;
    }
}
