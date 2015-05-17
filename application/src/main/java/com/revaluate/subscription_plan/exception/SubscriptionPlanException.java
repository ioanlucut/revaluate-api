package com.revaluate.subscription_plan.exception;

public class SubscriptionPlanException extends Exception {

    public SubscriptionPlanException() {
        super();
    }

    public SubscriptionPlanException(String message) {
        super(message);
    }

    public SubscriptionPlanException(String message, Throwable cause) {
        super(message, cause);
    }

    public SubscriptionPlanException(Throwable cause) {
        super(cause);
    }

    protected SubscriptionPlanException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}