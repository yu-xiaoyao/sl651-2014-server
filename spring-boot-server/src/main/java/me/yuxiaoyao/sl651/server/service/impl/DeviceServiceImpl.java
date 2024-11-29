package me.yuxiaoyao.sl651.server.service.impl;


import me.yuxiaoyao.sl651.netty.server.common.DeviceSendInfo;
import me.yuxiaoyao.sl651.server.service.DeviceService;
import org.springframework.stereotype.Service;

/**
 * @author kerryzhang on 2024/11/19
 * TODO 模拟数据库等.获取设备信息
 */

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceSendInfo info = DeviceSendInfo.builder()
            .deviceId("0011223344").stationId(5).password("03E8")
            .build();


    @Override
    public DeviceSendInfo getById(String id) {
        // TODO 自定义实现... 模拟数据库等.获取设备信息
        return info;
    }
}
