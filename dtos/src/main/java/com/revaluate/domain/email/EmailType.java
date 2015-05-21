package com.revaluate.domain.email;

public enum EmailType {

    CREATED_ACCOUNT("welcome-to-revaluate", EmailReply.REPLY),
    RESET_PASSWORD("reset-password", EmailReply.NO_REPLY);

    /**
     * Mandrill email template
     */
    private String emailTemplateName;

    /**
     * Email reply strategy.
     */
    private EmailReply emailReply;

    EmailType(String emailTemplateName, EmailReply emailReply) {
        this.emailTemplateName = emailTemplateName;
        this.emailReply = emailReply;
    }

    public String getEmailTemplateName() {
        return emailTemplateName;
    }

    public EmailReply getEmailReply() {
        return emailReply;
    }
}