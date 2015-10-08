package com.revaluate.reminder.exception;

public class ReminderException extends Exception {

    public ReminderException() {
        super();
    }

    public ReminderException(String message) {
        super(message);
    }

    public ReminderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReminderException(Throwable cause) {
        super(cause);
    }

    protected ReminderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}