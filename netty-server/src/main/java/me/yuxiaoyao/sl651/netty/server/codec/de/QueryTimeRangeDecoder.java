package me.yuxiaoyao.sl651.netty.server.codec.de;


import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.BaseMessage;
import me.yuxiaoyao.sl651.netty.server.message.ResponseMessage;

/**
 * @author kerryzhang on 2024/11/07
 */

public class QueryTimeRangeDecoder extends TelemetryTimeRangeFuncDecoder {

    @Override
    public int getFuncCode() {
        return 0x38;
    }


   /* @Override
    protected BaseMessage decodeObserverTimeMessage(MessageHeader header, int serialNo, String sendTime, String telemetryId, int telemetryType, String observerTime, ByteBuf buf) {
        var builder = TimeStepMessage.builder()
                .serialNo(serialNo)
                .sendTime(sendTime)
                .telemetryId(telemetryId)
                .telemetryType(telemetryType)
                .observerTime(observerTime);

        var elements = decodeCodingElements(buf);

        this.handleRemoveCodingElements(observerTime, elements,
                new RemoveElementAction(EleIdentifierEnum.TIME_STEP_CODE) {
                    @Override
                    public void onRemove(CodingElementItem element) {
                        // 时间步长码
                        builder.timeStepCode(element.getValue());
                    }
                });
        return builder.elements(elements).build();

    }*/

    @Override
    public ResponseMessage<?> makeResponse(MessageHeader header, BaseMessage message) {
        // 不需要回复
        return null;
    }
}
