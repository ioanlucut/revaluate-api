package com.revaluate.domain.email;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class AbstractSendTo {

    //-----------------------------------------------------------------
    // User related
    //-----------------------------------------------------------------
    protected int id;
    protected String firstName;
    protected String lastName;
    protected String email;

    //-----------------------------------------------------------------
    // Email related
    //-----------------------------------------------------------------
    protected int emailId;
    protected EmailType emailType;

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

    public int getEmailId() {
        return emailId;
    }

    public void setEmailId(int emailId) {
        this.emailId = emailId;
    }

    public EmailType getEmailType() {
        return emailType;
    }

    public void setEmailType(EmailType emailType) {
        this.emailType = emailType;
    }

    @Override
    public String toString() {
        return "SendTo{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", emailId=" + emailId +
                ", emailType=" + emailType +
                '}';
    }
}