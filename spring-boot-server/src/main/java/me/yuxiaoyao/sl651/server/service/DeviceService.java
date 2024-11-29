package me.yuxiaoyao.sl651.server.service;


import me.yuxiaoyao.sl651.netty.server.common.DeviceSendInfo;

/**
 * @author kerryzhang on 2024/11/19
 */

public interface DeviceService {

    DeviceSendInfo getById(String id);

}
