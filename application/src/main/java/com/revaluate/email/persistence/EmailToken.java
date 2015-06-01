package com.revaluate.email.persistence;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = EmailToken.EMAIL_WITH_TOKEN)
public class EmailToken extends Email {

    public static final String EMAIL_WITH_TOKEN = "EMAIL_WITH_TOKEN";

    @NotEmpty
    @Column
    private String token;

    private boolean tokenValidated;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isTokenValidated() {
        return tokenValidated;
    }

    public void setTokenValidated(boolean tokenValidated) {
        this.tokenValidated = tokenValidated;
    }

    @Override
    public String toString() {
        return "EmailToken{" +
                "token='" + token + '\'' +
                ", tokenValidated=" + tokenValidated +
                "} " + super.toString();
    }
}