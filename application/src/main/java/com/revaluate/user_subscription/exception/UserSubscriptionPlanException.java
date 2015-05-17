package com.revaluate.user_subscription.exception;

public class UserSubscriptionPlanException extends Exception {

    public UserSubscriptionPlanException() {
        super();
    }

    public UserSubscriptionPlanException(String message) {
        super(message);
    }

    public UserSubscriptionPlanException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserSubscriptionPlanException(Throwable cause) {
        super(cause);
    }

    protected UserSubscriptionPlanException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}