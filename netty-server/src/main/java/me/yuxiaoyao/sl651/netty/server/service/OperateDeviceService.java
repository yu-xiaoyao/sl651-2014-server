package me.yuxiaoyao.sl651.netty.server.service;


import lombok.Setter;
import me.yuxiaoyao.sl651.netty.server.tcp.TcpSessionManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;

/**
 * @author kerryzhang on 2024/11/07
 */

public class OperateDeviceService {

    public static OperateDeviceService get() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final OperateDeviceService INSTANCE = new OperateDeviceService();
    }

    private final Map<Integer, SynchronousQueue<Object>> sendQueue = new ConcurrentHashMap<>();

    @Setter
    private long defaultWaitTimeout = 20 * 1000L;

    public boolean isOnline(String deviceId) {
        return TcpSessionManager.get(deviceId) != null;
    }



    public void queryTelemetryRealTime(String deviceId, int serialNo, String sendTime, String telemetryId, int telemetryType) {

    }
}
