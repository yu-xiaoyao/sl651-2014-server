package me.yuxiaoyao.sl651.server.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author kerryzhang on 2024/11/06
 */

@ConfigurationProperties(prefix = "sl651")
@Component
@Data
public class Sl261Properties {

    private boolean enable = true;

    private String host;
    private int port;

    private boolean crcErrorReSend = false;

    private int readerIdleTimeSeconds = 0;
    private int writerIdleTimeSeconds = 0;
    private int allIdleTimeSeconds = 240;

}
