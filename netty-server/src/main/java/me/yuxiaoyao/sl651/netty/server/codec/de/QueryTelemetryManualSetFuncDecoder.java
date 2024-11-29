package me.yuxiaoyao.sl651.netty.server.codec.de;


import me.yuxiaoyao.sl651.netty.server.enums.MessageType;

/**
 * @author kerryzhang on 2024/11/07
 */

public class QueryTelemetryManualSetFuncDecoder extends TelemetryManualSetFuncDecoder {

    @Override
    public int getFuncCode() {
        // 6.6.4.12 中心站查询遥测站人工置数
        return 0x39;
    }


    @Override
    public MessageType messageType() {
        return MessageType.READ;
    }
}
