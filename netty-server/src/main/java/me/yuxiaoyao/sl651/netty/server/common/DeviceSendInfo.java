package me.yuxiaoyao.sl651.netty.server.common;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kerryzhang on 2024/11/18
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceSendInfo {
    private int stationId;
    private String deviceId;
    private String password;
}
