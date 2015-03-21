package com.revaluate;

import com.revaluate.account.persistence.EmailType;

public class SendTo {

    //-----------------------------------------------------------------
    // User related
    //-----------------------------------------------------------------
    private int id;
    private String firstName;
    private String lastName;
    private String email;

    //-----------------------------------------------------------------
    // Email related
    //-----------------------------------------------------------------
    private int emailTokenId;
    private String emailToken;
    private EmailType emailType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEmailTokenId() {
        return emailTokenId;
    }

    public void setEmailTokenId(int emailTokenId) {
        this.emailTokenId = emailTokenId;
    }

    public String getEmailToken() {
        return emailToken;
    }

    public void setEmailToken(String emailToken) {
        this.emailToken = emailToken;
    }

    public EmailType getEmailType() {
        return emailType;
    }

    public void setEmailType(EmailType emailType) {
        this.emailType = emailType;
    }
}