package com.revaluate.domain.email;

public enum EmailStatus {

    /**
     * If email is queued but not sent (waiting for an answer)
     */
    QUEUED,

    /**
     * If email was sent unsuccessful.
     */
    SENT_UNSUCCESSFUL,

    /**
     * If email was sent to the mandrill.
     */
    SENT;

}