package me.yuxiaoyao.sl651.netty.server.enums;


/**
 * @author kerryzhang on 2024/11/21
 */

class MessageTypeCode {

    public static final int REPORT = 0b001;
    public static final int READ = 0b010;
    public static final int WRITE = 0b100;
    public static final int REPORT_READ = REPORT | READ;
    public static final int REPORT_WRITE = REPORT | WRITE;
    public static final int READ_WRITE = READ | WRITE;
    public static final int ALL = REPORT | READ | WRITE;

    //public static boolean hasReport(int type) {
    //    return (type & REPORT) == REPORT;
    //}
    //
    //public static boolean hasRead(int type) {
    //    return (type & READ) == READ;
    //}
    //
    //public static boolean hasWrite(int type) {
    //    return (type & WRITE) == WRITE;
    //}
}
