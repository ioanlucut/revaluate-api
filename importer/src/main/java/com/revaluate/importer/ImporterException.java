package com.revaluate.importer;

public class ImporterException extends Exception {

    public ImporterException() {
        super();
    }

    public ImporterException(String message) {
        super(message);
    }

    public ImporterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImporterException(Throwable cause) {
        super(cause);
    }
}