package me.yuxiaoyao.sl651.server.common;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author kerryzhang on 2022/11/02
 */

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class ResultResponse<T> {
    public static final int SUCCESS = 200;
    public static final int FAILED = 500;

    private Integer code;
    private String message;
    private T data;
    private Long timestamp;
    private Boolean success;

    public ResultResponse() {

    }

    public ResultResponse(Builder<T> builder) {
        this.code = builder.code;
        this.message = builder.message;
        this.data = builder.data;
        this.timestamp = builder.timestamp;
        this.success = this.code == 0;
    }

    public boolean isSuccess() {
        return success != null && success;
    }

    public static <T> ResultResponse<T> success() {
        return new Builder<T>().setCode(SUCCESS).build();
    }

    public static <T> ResultResponse<T> success(String msg, T data) {
        return new Builder<T>().setMessage(msg).setData(data).setCode(SUCCESS).build();
    }

    public static <T> ResultResponse<T> success(T data) {
        return new Builder<T>().setData(data).setCode(SUCCESS).build();
    }


    public static <T> ResultResponse<T> failed(String message) {
        return new Builder<T>().setCode(FAILED).setMessage(message).build();
    }

    public static <T> ResultResponse<T> failed(T data, String message) {
        return new Builder<T>().setData(data).setCode(FAILED).setMessage(message).build();
    }

    public static class Builder<T> {
        private int code;
        private String message;
        private T data;
        private long timestamp = System.currentTimeMillis();

        public Builder<T> setCode(int code) {
            this.code = code;
            return this;
        }

        public Builder<T> setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> setData(T data) {
            this.data = data;
            return this;
        }

        public Builder<T> setTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ResultResponse<T> build() {
            return new ResultResponse<T>(this);
        }
    }
}
