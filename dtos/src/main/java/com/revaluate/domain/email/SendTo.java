package com.revaluate.domain.email;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class SendTo extends AbstractSendTo {

    //-----------------------------------------------------------------
    // Email related
    //-----------------------------------------------------------------
    private String emailToken;

    public String getEmailToken() {
        return emailToken;
    }

    public void setEmailToken(String emailToken) {
        this.emailToken = emailToken;
    }

    @Override
    public String toString() {
        return "SendTo{" +
                "emailToken='" + emailToken + '\'' +
                "} " + super.toString();
    }
}