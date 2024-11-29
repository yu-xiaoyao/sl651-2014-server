package me.yuxiaoyao.sl651.netty.server.exception;


/**
 * @author kerryzhang on 2024/11/06
 */

public class DecodeException extends RuntimeException {
    public DecodeException() {
        super();
    }

    public DecodeException(Throwable cause) {
        super(cause);
    }

    public DecodeException(String message) {
        super(message);
    }

    public DecodeException(String message, Throwable cause) {
        super(message, cause);
    }

    protected DecodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
