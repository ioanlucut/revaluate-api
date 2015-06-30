package com.revaluate.domain.account;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@GeneratePojoBuilder
public class FeedbackDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @NotBlank
    @Size(min = 1, max = 255)
    private String subject;

    @NotBlank
    @Size(min = 1, max = 3000)
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedbackDTO that = (FeedbackDTO) o;
        return Objects.equals(subject, that.subject) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, message);
    }

    @Override
    public String toString() {
        return "FeedbackDTO{" +
                "subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}