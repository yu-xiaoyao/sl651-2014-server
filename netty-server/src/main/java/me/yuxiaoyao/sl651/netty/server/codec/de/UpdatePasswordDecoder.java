package me.yuxiaoyao.sl651.netty.server.codec.de;


import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.codec.AbstractBaseConfigDecoder;
import me.yuxiaoyao.sl651.netty.server.enums.MessageType;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.BaseMessage;
import me.yuxiaoyao.sl651.netty.server.message.UpdatePasswordMessage;

/**
 * @author kerryzhang on 2024/11/19
 */

public class UpdatePasswordDecoder extends AbstractBaseConfigDecoder {

    @Override
    protected BaseMessage decodeStationMessage(MessageHeader header, int serialNo, String sendTime, String telemetryId, ByteBuf buf) {

        var builder = UpdatePasswordMessage.builder()
                .serialNo(serialNo)
                .sendTime(sendTime)
                .telemetryId(telemetryId);

        this.handleBaseConfig(buf, (flag, value) -> {
            if (flag == 0x03) {
                builder.newPassword((String) value);
            }
        });

        return builder.build();
    }

    @Override
    public int getFuncCode() {
        return 0x49;
    }

    @Override
    public MessageType messageType() {
        return MessageType.WRITE;
    }
}
