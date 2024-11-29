package me.yuxiaoyao.sl651.netty.server.exception;

/**
 * @author KerryMiBook on 24/11/8
 */


public class FrameInvalidException extends RuntimeException {
    public FrameInvalidException() {
        super();
    }

    public FrameInvalidException(String message) {
        super(message);
    }

    public FrameInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public FrameInvalidException(Throwable cause) {
        super(cause);
    }

    protected FrameInvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
