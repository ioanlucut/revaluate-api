package com.revaluate.domain.email;

public enum EmailStatus {

    SENT("sent"),
    QUEUED("queued"),
    REJECTED("rejected"),
    UNKNOWN("unknown");

    private String status;

    private EmailStatus(String emailTemplateName) {
        this.status = emailTemplateName;
    }

    public String getStatus() {
        return status;
    }

    public static EmailStatus from(String fromStatus) {
        if (fromStatus == null) {
            return UNKNOWN;
        }

        for (EmailStatus emailStatus : EmailStatus.values()) {
            if (fromStatus.equalsIgnoreCase(emailStatus.status)) {
                return emailStatus;
            }
        }

        return UNKNOWN;
    }

}