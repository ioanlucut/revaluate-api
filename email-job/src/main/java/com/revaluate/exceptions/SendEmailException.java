package com.revaluate.exceptions;

public class SendEmailException extends Exception {

    public SendEmailException() {
        super();
    }

    public SendEmailException(String message) {
        super(message);
    }

    public SendEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public SendEmailException(Throwable cause) {
        super(cause);
    }
}