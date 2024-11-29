package me.yuxiaoyao.sl651.netty.server.enums;


import lombok.Getter;

/**
 * @author kerryzhang on 2024/11/18
 */

@Getter
public enum MessageType {
    REPORT("report", true, MessageTypeCode.REPORT),
    READ("read", true, MessageTypeCode.READ),
    WRITE("write", true, MessageTypeCode.WRITE),
    REPORT_READ("report_read", false, MessageTypeCode.REPORT_READ),
    REPORT_WRITE("report_write", false, MessageTypeCode.REPORT_WRITE),
    READ_WRITE("read_write", false, MessageTypeCode.READ_WRITE),
    ALL("all", false, MessageTypeCode.ALL);


    private final String type;
    private final boolean primitiveType;
    private final int code;

    MessageType(String type, boolean primitiveType, int code) {
        this.type = type;
        this.primitiveType = primitiveType;
        this.code = code;
    }

    public boolean hasReport() {
        return (code & MessageTypeCode.REPORT) == MessageTypeCode.REPORT;
    }

    public boolean hasRead() {
        return (code & MessageTypeCode.READ) == MessageTypeCode.READ;
    }

    public boolean hasWrite() {
        return (code & MessageTypeCode.WRITE) == MessageTypeCode.WRITE;
    }
}
