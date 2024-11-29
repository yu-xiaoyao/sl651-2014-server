package me.yuxiaoyao.sl651.netty.server.common;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.yuxiaoyao.sl651.netty.server.enums.SendStateEnum;

/**
 * @author kerryzhang on 2024/11/18
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendResult<S, R> {
    private SendStateEnum state;
    private S send;
    private R result;

    public boolean isSuccess() {
        return state == SendStateEnum.SUCCESS;
    }

    public static <S, R> SendResult<S, R> success(S send, R result) {
        return new SendResult<S, R>(SendStateEnum.SUCCESS, send, result);
    }

    public static <S, R> SendResult<S, R> offline(S send) {
        return new SendResult<>(SendStateEnum.OFFLINE, send, null);
    }

    public static <S, R> SendResult<S, R> ofFailed(SendStateEnum state, S send) {
        return new SendResult<>(state, send, null);
    }
}
