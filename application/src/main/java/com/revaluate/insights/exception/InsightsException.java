package com.revaluate.insights.exception;

public class InsightsException extends Exception {

    public InsightsException() {
        super();
    }

    public InsightsException(String message) {
        super(message);
    }

    public InsightsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsightsException(Throwable cause) {
        super(cause);
    }

    protected InsightsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}