package com.revaluate.expense.exception;

public class ExpenseException extends Exception {

    public ExpenseException() {
        super();
    }

    public ExpenseException(String message) {
        super(message);
    }

    public ExpenseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpenseException(Throwable cause) {
        super(cause);
    }

    protected ExpenseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}