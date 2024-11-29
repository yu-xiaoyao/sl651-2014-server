package me.yuxiaoyao.sl651.netty.server.codec.de;


/**
 * @author kerryzhang on 2024/11/06
 */

public class TestFuncDecoder extends QueryTelemetryRealTimeFuncDecoder {


    @Override
    public int getFuncCode() {
        // 6.6.4.3 测试报
        return 0x30;
    }

    /*@Override
    protected BaseMessage decodeObserverTimeMessage(MessageHeader header, int serialNo, String sendTime, String telemetryId, int telemetryType, String observerTime, ByteBuf buf) {

        var builder = TelemetryRealTimeMessage.builder()
                .serialNo(serialNo)
                .sendTime(sendTime)
                .telemetryId(telemetryId)
                .telemetryType(telemetryType)
                .observerTime(observerTime);

        var elements = decodeCodingElements(buf);

        this.handleRemoveCodingElements(observerTime, elements,
                new RemoveElementAction(EleIdentifierEnum.CURRENT_PRECIPITATION) {
                    @Override
                    public void onRemove(CodingElementItem element) {
                        // 当前降水量
                        builder.currentPrecipitation((Double) element.getValue());
                    }
                },
                new RemoveElementAction(EleIdentifierEnum.PRECIPITATION_ACCUMULATION) {
                    @Override
                    public void onRemove(CodingElementItem element) {
                        // 降水量累计值
                        builder.accumulatedPrecipitation((Double) element.getValue());
                    }
                },
                new RemoveElementAction(EleIdentifierEnum.INSTANT_WATER_LEVEL) {
                    @Override
                    public void onRemove(CodingElementItem element) {
                        // 瞬时水位
                        builder.instantaneousWaterLevel((Double) element.getValue());
                    }
                },
                new RemoveElementAction(EleIdentifierEnum.VOLTAGE) {
                    @Override
                    public void onRemove(CodingElementItem element) {
                        // 电压
                        builder.voltage((Double) element.getValue());
                    }
                });

        return TelemetryRealTimeMessage.builder()
                .serialNo(serialNo)
                .sendTime(sendTime)
                .telemetryId(telemetryId)
                .telemetryType(telemetryType)
                .observerTime(observerTime)
                .elements(elements)
                .build();
    }*/
}
