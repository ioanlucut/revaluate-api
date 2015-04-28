package com.revaluate.insights.exception;

public class InsightException extends Exception {

    public InsightException() {
        super();
    }

    public InsightException(String message) {
        super(message);
    }

    public InsightException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsightException(Throwable cause) {
        super(cause);
    }

    protected InsightException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}