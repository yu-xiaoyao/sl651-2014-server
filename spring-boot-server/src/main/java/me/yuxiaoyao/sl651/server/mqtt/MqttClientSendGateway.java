package me.yuxiaoyao.sl651.server.mqtt;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * @author kerryzhang on 2024/11/18
 */


@MessagingGateway(defaultRequestChannel = MqttChannelName.OUTBOUND)
public interface MqttClientSendGateway {
    /**
     * 发送消息
     *
     * @param payload 发送的消息
     */
    void sendToMqtt(String payload);

    /**
     * 指定topic消息发送
     *
     * @param topic   指定topic
     * @param payload 消息
     */
    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, String payload);

    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, byte[] payload);

    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, String payload);

    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, byte[] payload);
}
