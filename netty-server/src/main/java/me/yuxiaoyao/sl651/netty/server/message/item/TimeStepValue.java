package me.yuxiaoyao.sl651.netty.server.message.item;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kerryzhang on 2024/11/19
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TimeStepValue {
    private Unit unit;
    /**
     * 10 进制
     */
    private int value;

    public enum Unit {
        DAY,
        HOUR,
        MINUTE,
    }

    /**
     * 合并,并转换为 BCD 码
     *
     * @return
     */
    public String getTimeStepCode() {
        String code;
        if (value < 10) {
            code = "0" + value;
        } else {
            code = "" + value;
        }
        if (unit == Unit.MINUTE) {
            return "0000" + code;
        } else if (unit == Unit.HOUR) {
            return "00" + code + "00";
        }
        return code + "0000";
    }


    public static TimeStepValue ofMinute(int value) {
        return new TimeStepValue(Unit.MINUTE, value);
    }

    public static TimeStepValue ofHour(int value) {
        return new TimeStepValue(Unit.HOUR, value);
    }

    public static TimeStepValue ofDay(int value) {
        return new TimeStepValue(Unit.DAY, value);
    }
}
