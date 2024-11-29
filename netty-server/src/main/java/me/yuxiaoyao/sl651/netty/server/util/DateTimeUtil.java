package me.yuxiaoyao.sl651.netty.server.util;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author kerryzhang on 2024/11/18
 */

public class DateTimeUtil {

    public static final DateTimeFormatter BCD_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmmss");
    public static final DateTimeFormatter BCD_DATE_TIME_RANGE = DateTimeFormatter.ofPattern("yyMMddHH");

    public static long formatSendTime(String sendTime) {
        var dt = LocalDateTime.parse(sendTime, BCD_DATE_TIME_FORMATTER);
        return dt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取长度. len = 6
     *
     * @return
     */
    public static byte[] getSendTimeAsBytes() {
        String format = BCD_DATE_TIME_FORMATTER.format(LocalDateTime.now());
        return HexUtil.hexStringToByteArray(format);
    }

    public static String getSendTime() {
        return BCD_DATE_TIME_FORMATTER.format(LocalDateTime.now());
    }

    public static String formatTimeRange(LocalDateTime time) {
        return BCD_DATE_TIME_RANGE.format(time);
    }

    public static LocalDateTime getTimeRange(String time) {
        return LocalDateTime.parse(time, BCD_DATE_TIME_RANGE);
    }

}
