package com.revaluate.domain.email;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class SendFeedbackTo extends AbstractSendTo {

    private String subject;
    private String message;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SendFeedbackTo{" +
                "subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                "} " + super.toString();
    }
}