package me.yuxiaoyao.sl651.netty.server.header;


import lombok.Builder;
import lombok.Data;

/**
 * @author kerryzhang on 2024/11/06
 */

@Data
@Builder
public class MessageHeader {
    private byte[] startFrame;
    private int stationId;
    private String telemetryId;
    private String password;
    private int funcCode;
    private boolean isM3Mode;
    private int packetStartCode;
    private int packetEndCode;
}
