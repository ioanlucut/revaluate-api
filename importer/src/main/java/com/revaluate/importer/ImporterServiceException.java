package com.revaluate.importer;

public class ImporterServiceException extends Exception {

    public ImporterServiceException() {
        super();
    }

    public ImporterServiceException(String message) {
        super(message);
    }

    public ImporterServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImporterServiceException(Throwable cause) {
        super(cause);
    }
}