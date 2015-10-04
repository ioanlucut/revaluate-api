package com.revaluate.slack;

public class SlackException extends Exception {

    public SlackException() {
        super();
    }

    public SlackException(String message) {
        super(message);
    }

    public SlackException(String message, Throwable cause) {
        super(message, cause);
    }

    public SlackException(Throwable cause) {
        super(cause);
    }
}