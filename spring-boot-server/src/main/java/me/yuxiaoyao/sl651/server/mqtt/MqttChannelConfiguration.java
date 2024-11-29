package me.yuxiaoyao.sl651.server.mqtt;


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.ExecutorChannel;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author kerryzhang on 2024/11/18
 */


@Configuration
public class MqttChannelConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(MqttChannelConfiguration.class);


    @Bean(name = "mqttThreadPoolExecutor")
    public ThreadPoolExecutor mqttThreadPoolExecutor() {
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("mqtt-channel-%d").build();
        return new ThreadPoolExecutor(4, 20, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), factory, new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @Bean(MqttChannelName.INBOUND)
    public MessageChannel inboundChannel() {
        return new DirectChannel();
    }

    @Bean(MqttChannelName.OUTBOUND)
    public MessageChannel outboundChannel(@Qualifier("mqttThreadPoolExecutor") ThreadPoolExecutor mqttThreadPoolExecutor) {
        return new ExecutorChannel(mqttThreadPoolExecutor);
    }

    @Bean(MqttChannelName.DEFAULT)
    public MessageChannel defaultChannel() {
        return new DirectChannel();
    }

    @Bean(MqttChannelName.ERROR)
    public MessageChannel errorChannel() {
        return new DirectChannel();
    }


    @ServiceActivator(inputChannel = MqttChannelName.DEFAULT)
    public MessageHandler defaultHandler() {
        return message -> {
            if (logger.isTraceEnabled()) {
                logger.trace("The default channel does not handle messages.\nTopic: {}\nPayload: {}", message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC), message.getPayload());
            }
        };
    }

    @ServiceActivator(inputChannel = MqttChannelName.ERROR)
    public MessageHandler errorHandler() {
        return message -> logger.error("The Error channel. \nTopic: {}\nPayload: {}", message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC), message.getPayload());
    }


}
