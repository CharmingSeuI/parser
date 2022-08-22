package com.copus.parser.exception;

public class ExtraAuthorInfoException extends RuntimeException {
    public ExtraAuthorInfoException() {
        super();
    }

    public ExtraAuthorInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExtraAuthorInfoException(Throwable cause) {
        super(cause);
    }

    protected ExtraAuthorInfoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ExtraAuthorInfoException(String s) {
    }
}
