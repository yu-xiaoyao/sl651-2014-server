package me.yuxiaoyao.sl651.netty.server.codec;


import me.yuxiaoyao.sl651.netty.server.codec.de.*;
import me.yuxiaoyao.sl651.netty.server.codec.rw.ReadTelemetryBaseConfigFuncDecoder;
import me.yuxiaoyao.sl651.netty.server.codec.rw.ReadTelemetryRuntimeConfigFuncDecoder;
import me.yuxiaoyao.sl651.netty.server.codec.rw.WriteTelemetryBaseConfigFuncDecoder;
import me.yuxiaoyao.sl651.netty.server.codec.rw.WriteTelemetryRuntimeConfigFuncDecoder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kerryzhang on 2024/11/06
 */

public class Decoders {

    private static final Map<Integer, IFuncDecoder<?>> DECODER_MAP = new ConcurrentHashMap<>();

    static {
        // 2F
        autoRegister(new HeartBeatFuncDecoder());
        // 30
        autoRegister(new TestFuncDecoder());
        // 31
        autoRegister(new TelemetryTimeRangeFuncDecoder());
        // 32
        autoRegister(new TelemetryTimingFuncDecoder());
        // 33
        autoRegister(new TelemetryIncreaseFuncDecoder());
        // 34
        autoRegister(new TelemetryHourFuncDecoder());
        // 35
        autoRegister(new TelemetryManualSetFuncDecoder());
        // 36 图片收集
        autoRegister(new ImageCollectFuncDecoder());

        // 37
        autoRegister(new QueryTelemetryRealTimeFuncDecoder());
        // 38
        autoRegister(new QueryTimeRangeDecoder());

        // 39
        autoRegister(new QueryTelemetryManualSetFuncDecoder());
        // 3A
        autoRegister(new QueryElementRealTimeDecoder());

        // 40
        autoRegister(new WriteTelemetryBaseConfigFuncDecoder());
        // 41 read config
        autoRegister(new ReadTelemetryBaseConfigFuncDecoder());

        // 42
        autoRegister(new ReadTelemetryRuntimeConfigFuncDecoder());
        // 43
        autoRegister(new WriteTelemetryRuntimeConfigFuncDecoder());

        // 44
        autoRegister(new WaterPumpRealTimeDecoder());

        // 45
        autoRegister(new QueryTelemetrySoftVersionFuncDecoder());
        // 46
        autoRegister(new QueryTelemetryStateAndAlarmFuncDecoder());
        // 47
        autoRegister(new InitDataStorageDecoder());
        // 48
        autoRegister(new RestoreFactorySettingsDecoder());
        // 49
        autoRegister(new UpdatePasswordDecoder());
        // 4A
        autoRegister(new UpdateClockDecoder());

        // 50
        autoRegister(new QueryEventRecordDecoder());
        // 51
        autoRegister(new QueryTelemetryClockFuncDecoder());
    }


    public static IFuncDecoder<?> getDecoder(int funcCode) {
        return DECODER_MAP.get(funcCode);
    }

    private static void autoRegister(IFuncCodeProvider codec) {
        if (codec instanceof IFuncDecoder<?> decoder) {
            registerDecoder(decoder.getFuncCode(), decoder);
        }
    }

    private static void registerDecoder(int code, IFuncDecoder<?> decoder) {
        DECODER_MAP.put(code, decoder);
    }


    public static void main(String[] args) {
        int start = 0xF0;
        int end = 0xFD;

        for (int i = start; i <= end; i++) {
            var b = i >= 0x76 && i <= 0xEF;

            System.out.println(i + " - " + b);
        }

        System.out.println("------------------------");
        System.out.println(0x76);
        System.out.println(0xEF);
        System.out.println(0xFD - 0xF0);
    }

}
