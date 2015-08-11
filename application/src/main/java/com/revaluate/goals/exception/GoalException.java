package com.revaluate.goals.exception;

public class GoalException extends Exception {

    public GoalException() {
        super();
    }

    public GoalException(String message) {
        super(message);
    }

    public GoalException(String message, Throwable cause) {
        super(message, cause);
    }

    public GoalException(Throwable cause) {
        super(cause);
    }

    protected GoalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}