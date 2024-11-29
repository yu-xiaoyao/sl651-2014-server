package me.yuxiaoyao.sl651.server.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @author kerryzhang on 2023/07/25
 */


@Component
@ConfigurationProperties(prefix = "mqtt")
@Data
public class MqttProperties {
    private boolean enabled;
    private List<String> urls;
    private String username;
    private String password;
    /**
     * 是否固定 ClientId, false: 加上当前连接时间
     */
    private boolean fixClientId = false;
    private String clientId;
    /**
     * 订阅 Topic
     */
    private Set<String> topics;
    private boolean autoReconnect = false;
    private int keepAliveInterval = 10;
    private int qos = 0;
}
