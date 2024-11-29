package me.yuxiaoyao.sl651.netty.server.codec.rw;

import me.yuxiaoyao.sl651.netty.server.read.IObjectReader;
import me.yuxiaoyao.sl651.netty.server.telemetry.TelemetryBaseConfigTable;

/**
 * @author KerryMiBook on 24/11/8
 */


public class ReadTelemetryBaseConfigFuncDecoder extends AbstractTelemetryConfigFuncDecoder {
    @Override
    public int getFuncCode() {
        return 0x41;
    }

    //@Override
    //protected TelemetryConfig getConfig(int configFlag) {
    //    return TelemetryBaseConfigTable.getConfig(configFlag);
    //}

    @Override
    protected IObjectReader<?> getDataDefine(int configFlag) {
        return TelemetryBaseConfigTable.getObjectReader(configFlag);
    }
}
