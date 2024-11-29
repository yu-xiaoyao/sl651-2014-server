package me.yuxiaoyao.sl651.netty.server.codec.rw;

import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.codec.AbstractStationFuncDecoder;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.BaseMessage;
import me.yuxiaoyao.sl651.netty.server.message.ResponseMessage;
import me.yuxiaoyao.sl651.netty.server.message.TelemetryConfigMessage;
import me.yuxiaoyao.sl651.netty.server.message.common.ConfigMessageItem;
import me.yuxiaoyao.sl651.netty.server.read.IObjectReader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kerryzhang on 2024/11/09
 */

public abstract class AbstractTelemetryConfigFuncDecoder extends AbstractStationFuncDecoder {

    @Override
    protected BaseMessage decodeStationMessage(MessageHeader header, int serialNo, String sendTime, String stationId, ByteBuf buf) {
        List<ConfigMessageItem> configs = new ArrayList<>();
        while (buf.isReadable()) {
            // 表D.1 遥测站基本配置表
            var configFlag = buf.readByte() & 0xFF;
            var dataDefine = getDataDefine(configFlag);
            Object value = dataDefine.read(buf);
            configs.add(new ConfigMessageItem(configFlag, value));
        }
        return TelemetryConfigMessage.builder()
                .serialNo(serialNo)
                .sendTime(sendTime)
                .telemetryId(stationId)
                .configs(configs)
                .build();
    }

    protected abstract IObjectReader<?> getDataDefine(int configFlag);

    @Override
    public ResponseMessage<?> makeResponse(MessageHeader header, BaseMessage message) {
        return null;
    }
}
