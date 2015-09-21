package com.revaluate.oauth.exception;

public class AppIntegrationException extends Exception {

    public AppIntegrationException() {
        super();
    }

    public AppIntegrationException(String message) {
        super(message);
    }

    public AppIntegrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppIntegrationException(Throwable cause) {
        super(cause);
    }

}