package me.yuxiaoyao.sl651.server.mqtt;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import me.yuxiaoyao.sl651.netty.server.event.DeviceEventPublisher;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.ElementsMessage;
import me.yuxiaoyao.sl651.netty.server.message.item.ElementItem;
import me.yuxiaoyao.sl651.netty.server.util.DateTimeUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kerryzhang on 2024/11/06
 * <p>
 * 自定义事件转发. 此处为示例
 */


@ConditionalOnProperty(prefix = "mqtt", value = "enabled")
@Component
@RequiredArgsConstructor
public class MqttDeviceEventPublisher implements DeviceEventPublisher {

    public static final String MQTT_TOPIC = "/message/properties/report/sl651-2014";

    private final MqttClientSendGateway sendGateway;
    private final ObjectMapper objectMapper;

    @Override
    public void onMessageReport(MessageHeader header, Object decodeMessage) {
        //TODO 自定义保存或者转发事件.....
        if (decodeMessage instanceof ElementsMessage codingElementsMessage) {
            onReceiveCodingElementsMessage(header, codingElementsMessage);
        }
    }

    protected void onReceiveCodingElementsMessage(MessageHeader header, ElementsMessage codingElementsMessage) {
        var message = new Message();
        message.setDeviceId(header.getTelemetryId());
        message.setMessageId(generateMessageId(header, codingElementsMessage));
        message.setTimestamp(DateTimeUtil.formatSendTime(codingElementsMessage.getSendTime()));
        List<ElementItem> elements = codingElementsMessage.getElements();
        Map<String, Object> props = new LinkedHashMap<>(elements.size());

        for (ElementItem element : elements) {
            var key = convertEleIdToIdentify(element.getEleId());
            props.put(key, element.getValue());
        }

        message.setProps(props);
        sendGateway.sendToMqtt(MQTT_TOPIC, objectToByteArray(message));
    }

    private String convertEleIdToIdentify(Integer eleId) {
        //TODO 按需要将 eleId 转换为 字符串,方便后续处理, 这时暂时使用16进制
        return Integer.toHexString(eleId);
    }

    private String generateMessageId(MessageHeader header, ElementsMessage codingElementsMessage) {
        return header.getTelemetryId() + "_" + header.getStationId() + "_" +
                codingElementsMessage.getObserverTime() + "_" + codingElementsMessage.getSerialNo();
    }


    @SneakyThrows
    private byte[] objectToByteArray(Object obj) {
        return objectMapper.writeValueAsBytes(obj);
    }

    @Override
    public void onConnect(String id) {

    }


    @Override
    public void onDisconnect(String id) {

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class Message {
        /**
         * 唯一的消息ID
         */
        private String messageId;
        private String deviceId;
        private Long timestamp;
        private String type = "report";
        private Map<String, Object> props;
    }
}
