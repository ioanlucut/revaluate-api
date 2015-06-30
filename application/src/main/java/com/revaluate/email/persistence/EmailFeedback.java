package com.revaluate.email.persistence;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = EmailFeedback.EMAIL_FEEDBACK)
public class EmailFeedback extends Email {

    public static final String EMAIL_FEEDBACK = "EMAIL_FEEDBACK";
    public static final int MESSAGE_WIDTH = 3000;

    @NotEmpty
    @Column
    private String subject;

    @NotEmpty
    @Column(length = MESSAGE_WIDTH)
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
        return "EmailFeedback{" +
                "subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                "} " + super.toString();
    }
}