package me.yuxiaoyao.sl651.netty.server.codec.rw;

import me.yuxiaoyao.sl651.netty.server.read.IObjectReader;
import me.yuxiaoyao.sl651.netty.server.telemetry.TelemetryRuntimeConfigTable;

/**
 * @author KerryMiBook on 24/11/8
 */


public class ReadTelemetryRuntimeConfigFuncDecoder extends AbstractTelemetryConfigFuncDecoder {
    @Override
    public int getFuncCode() {
        return 0x43;
    }

    //@Override
    //protected TelemetryConfig getConfig(int configFlag) {
    //    return TelemetryRuntimeConfigTable.getConfig(configFlag);
    //}
    @Override
    protected IObjectReader<?> getDataDefine(int configFlag) {
        return TelemetryRuntimeConfigTable.getObjectReader(configFlag);
    }
}
