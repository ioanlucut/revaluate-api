package com.revaluate.color.exception;

public class ColorException extends Exception {

    public ColorException() {
        super();
    }

    public ColorException(String message) {
        super(message);
    }

    public ColorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ColorException(Throwable cause) {
        super(cause);
    }

    protected ColorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}