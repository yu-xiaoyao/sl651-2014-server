package me.yuxiaoyao.sl651.netty.server.tcp;

import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author kerryzhang on 2024/11/07
 */

public class TcpSessionManager {

    public static final AttributeKey<String> CHANNEL_GROUP = AttributeKey.valueOf("netty.channel");
    public static final ChannelGroup TCP_CHANNEL_GROUP = new DefaultChannelGroup("tcpChannelGroup", GlobalEventExecutor.INSTANCE);
    /**
     * 用于下发指令
     */
    private static ConcurrentMap<String, ChannelId> tcpDeviceChannelMap = new ConcurrentHashMap<>();

    public static ChannelId put(String key, ChannelId currentId) {
        return tcpDeviceChannelMap.put(key, currentId);
    }

    public static ChannelId get(String key) {
        return tcpDeviceChannelMap.get(key);
    }

    public static ChannelId remove(String key) {
        return tcpDeviceChannelMap.remove(key);
    }

}
