//package me.yuxiaoyao.sl651.netty.server.telemetry.define;
//
//import com.tomgibara.bits.BitReader;
//import com.tomgibara.bits.Bits;
//import io.netty.buffer.ByteBuf;
//import me.yuxiaoyao.sl651.netty.server.define.AbstractDynamicLengthDataDefine;
//import me.yuxiaoyao.sl651.netty.server.message.item.TelemetryCollectEleSetting;
//import me.yuxiaoyao.sl651.netty.server.util.ByteArrayUtil;
//
///**
// * @author KerryMiBook on 24/11/9
// */
//
//
//public class TelemetryCollectEleSettingDataDefine extends AbstractDynamicLengthDataDefine<TelemetryCollectEleSetting> {
//
//    @Override
//    protected TelemetryCollectEleSetting doRead(int dataLen, int decLen, ByteBuf buf) {
//        var bytes = ByteArrayUtil.readByteBuf(buf, dataLen);
//        return parse(bytes);
//    }
//
//    /**
//     * 表D.2 遥测站监测要素定义表
//     *
//     * @param bytes
//     * @return
//     */
//    public static TelemetryCollectEleSetting parse(byte[] bytes) {
//        BitReader br = Bits.readerFrom(bytes);
//        var builder = TelemetryCollectEleSetting.builder()
//                .precipitation(br.readBoolean())
//                .emission(br.readBoolean())
//                .windDirection(br.readBoolean())
//                .windSpeed(br.readBoolean())
//                .temperature(br.readBoolean())
//                .humidity(br.readBoolean())
//                .groundTemperature(br.readBoolean())
//                .pressure(br.readBoolean())                 // byte  1
//                .waterLevel8(br.readBoolean())
//                .waterLevel7(br.readBoolean())
//                .waterLevel6(br.readBoolean())
//                .waterLevel5(br.readBoolean())
//                .waterLevel4(br.readBoolean())
//                .waterLevel3(br.readBoolean())
//                .waterLevel2(br.readBoolean())
//                .waterLevel1(br.readBoolean())              // byte 2
//                .groundWaterDepth(br.readBoolean())
//                .picture(br.readBoolean())
//                .wave(br.readBoolean())
//                .gateOpening(br.readBoolean())
//                .waterAmount(br.readBoolean())
//                .flowSpeed(br.readBoolean())
//                .flowAmount(br.readBoolean())
//                .waterPressure(br.readBoolean())            // byte 3
//                .waterMeter8(br.readBoolean())
//                .waterMeter7(br.readBoolean())
//                .waterMeter6(br.readBoolean())
//                .waterMeter5(br.readBoolean())
//                .waterMeter4(br.readBoolean())
//                .waterMeter3(br.readBoolean())
//                .waterMeter2(br.readBoolean())
//                .waterMeter1(br.readBoolean())             // byte 4
//                .soilMoisture100Cm(br.readBoolean())
//                .soilMoisture80Cm(br.readBoolean())
//                .soilMoisture60Cm(br.readBoolean())
//                .soilMoisture50Cm(br.readBoolean())
//                .soilMoisture40Cm(br.readBoolean())
//                .soilMoisture30Cm(br.readBoolean())
//                .soilMoisture20Cm(br.readBoolean())
//                .soilMoisture10Cm(br.readBoolean())        // byte 5
//                .phValue(br.readBoolean())
//                .solubleOxygen(br.readBoolean())
//                .electricConductivity(br.readBoolean())
//                .turbidity(br.readBoolean())
//                .redoxPotential(br.readBoolean())
//                .permanganateExponent(br.readBoolean())
//                .ammoniaNitrogen(br.readBoolean())
//                .waterTemperature(br.readBoolean())         // byte 6
//                .totalOrganicCarbon(br.readBoolean())
//                .totalNitrogen(br.readBoolean())
//                .totalPhosphorus(br.readBoolean())
//                .zinc(br.readBoolean())
//                .selenium(br.readBoolean())
//                .arsenic(br.readBoolean())
//                .totalMercury(br.readBoolean())
//                .cadmium(br.readBoolean());                 // byte 7
//
//        // byte 8
//        br.skipBits(5);
//        builder.chlA(br.readBoolean())
//                .copper(br.readBoolean())
//                .lead(br.readBoolean());
//
//        return builder.build();
//    }
//
//    @Override
//    public ByteBuf write(TelemetryCollectEleSetting data) {
//        return null;
//    }
//}
