package com.revaluate.account.persistence;

public enum EmailType {

    CREATED_ACCOUNT("welcome-to-revaluate"),
    RESET_PASSWORD("reset-password");

    /**
     * Mandrill email template
     */
    private String emailTemplateName;

    private EmailType(String emailTemplateName) {
        this.emailTemplateName = emailTemplateName;
    }

    public String getEmailTemplateName() {
        return emailTemplateName;
    }
}