package me.yuxiaoyao.sl651.server.config;


import me.yuxiaoyao.sl651.netty.server.event.DeviceEventPublisher;
import me.yuxiaoyao.sl651.netty.server.event.LogDeviceEventPublisher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kerryzhang on 2024/11/18
 */

@Configuration
public class DeviceEventPublisherConfiguration {

    /**
     * 配置默认
     *
     * @return
     */
    @ConditionalOnMissingBean(DeviceEventPublisher.class)
    @Bean("logDeviceEventPublisher")
    public DeviceEventPublisher logDeviceEventPublisher() {
        return new LogDeviceEventPublisher();
    }

}
