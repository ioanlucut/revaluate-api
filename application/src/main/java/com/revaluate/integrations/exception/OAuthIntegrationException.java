package com.revaluate.integrations.exception;

public class OauthIntegrationException extends Exception {

    public OauthIntegrationException() {
        super();
    }

    public OauthIntegrationException(String message) {
        super(message);
    }

    public OauthIntegrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public OauthIntegrationException(Throwable cause) {
        super(cause);
    }

}