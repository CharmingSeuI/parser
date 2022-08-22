package com.copus.parser.exception;

public class ExtraMetaInfoException extends RuntimeException {
    public ExtraMetaInfoException() {
        super();
    }

    public ExtraMetaInfoException(String message) {
        super(message);
    }

    public ExtraMetaInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExtraMetaInfoException(Throwable cause) {
        super(cause);
    }

    protected ExtraMetaInfoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
