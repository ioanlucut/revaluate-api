package com.revaluate.payment.exception;

import java.util.ArrayList;
import java.util.List;

public class PaymentStatusException extends Exception {

    private List<String> errors = new ArrayList<>();

    public PaymentStatusException() {
        super();
    }

    public PaymentStatusException(String message) {
        super(message);
    }

    public PaymentStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentStatusException(Throwable cause) {
        super(cause);
    }

    public PaymentStatusException(List<String> errors) {
        super();
    }

    protected PaymentStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public List<String> getErrors() {
        return errors;
    }
}