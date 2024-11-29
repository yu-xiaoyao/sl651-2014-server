package me.yuxiaoyao.sl651.server.mqtt;


import lombok.RequiredArgsConstructor;
import me.yuxiaoyao.sl651.server.properties.MqttProperties;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.util.Arrays;

/**
 * @author kerryzhang on 2024/11/18
 */

@ConditionalOnProperty(prefix = "mqtt", value = "enabled")
@Configuration
@RequiredArgsConstructor
public class MqttConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(MqttConfiguration.class);

    private final MqttProperties mqttProperties;

    @Bean
    public DefaultMqttPahoClientFactory mqttClientFactory() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(mqttProperties.getUrls().toArray(new String[0]));
        options.setUserName(mqttProperties.getUsername());
        options.setPassword(mqttProperties.getPassword().toCharArray());
        options.setKeepAliveInterval(mqttProperties.getKeepAliveInterval());

        options.setAutomaticReconnect(mqttProperties.isAutoReconnect());
        options.setConnectionTimeout(10);

        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(options);
        return factory;
    }


    @Bean(name = "mqttAdapter")
    public MqttPahoMessageDrivenChannelAdapter mqttAdapter(MqttPahoClientFactory mqttClientFactory,
                                                           @Qualifier(MqttChannelName.INBOUND) MessageChannel inboundChannel,
                                                           @Qualifier(MqttChannelName.ERROR) MessageChannel errorChannel) {

        String clientId = getClientId(false);

        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(clientId, mqttClientFactory);

        logger.info("MQTT初始化. url: {}, inClientId = {}, autoReconnect: {}", mqttProperties.getUrls(), clientId, mqttProperties.isAutoReconnect());

        if (mqttProperties.getTopics() != null) {
            var topics = mqttProperties.getTopics().toArray(new String[0]);
            if (topics.length > 0) {
                adapter.addTopic(topics);
            }
            logger.info("MQTT初始化. defaultSubscribeTopics: {}", Arrays.deepToString(topics));
        }
        DefaultPahoMessageConverter defaultPahoMessageConverter = new DefaultPahoMessageConverter();
        // 按字节接收消息
        // defaultPahoMessageConverter.setPayloadAsBytes(true);
        adapter.setConverter(defaultPahoMessageConverter);
        // 设置QoS
        adapter.setOutputChannel(inboundChannel);
        adapter.setErrorChannel(errorChannel);
        adapter.setQos(mqttProperties.getQos());

        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = MqttChannelName.OUTBOUND)
    public MessageHandler mqttOutbound(MqttPahoClientFactory mqttClientFactory) {

        String clientId = getClientId(true);

        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(clientId, mqttClientFactory);
        messageHandler.setAsync(true);
        // 设置默认的topic
        // messageHandler.setDefaultTopic(mqttProperties.getDefaultSendTopic());
        messageHandler.setDefaultQos(mqttProperties.getQos());

        DefaultPahoMessageConverter defaultPahoMessageConverter = new DefaultPahoMessageConverter();

        messageHandler.setConverter(defaultPahoMessageConverter);
        return messageHandler;
    }

    private String getClientId(boolean isSend) {
        String clientId = mqttProperties.getClientId();
        if (clientId == null) {
            clientId = System.currentTimeMillis() + "";
        } else {
            if (mqttProperties.isFixClientId()) {
                if (isSend) {
                    return "S_" + clientId;
                }
                return "R_" + clientId;
            }
            clientId = clientId + "_" + System.currentTimeMillis();
        }
        if (isSend) {
            return "S_" + clientId;
        }
        return "R_" + clientId;
    }
}
