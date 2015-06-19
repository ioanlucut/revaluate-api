package com.revaluate.domain.email;

public enum MandrillEmailStatus {

    SENT("sent"),
    QUEUED("queued"),
    REJECTED("rejected"),
    UNKNOWN("unknown");

    private String status;

    MandrillEmailStatus(String emailTemplateName) {
        this.status = emailTemplateName;
    }

    public String getStatus() {
        return status;
    }

    public static MandrillEmailStatus from(String fromStatus) {
        if (fromStatus == null) {
            return UNKNOWN;
        }

        for (MandrillEmailStatus mandrillEmailStatus : MandrillEmailStatus.values()) {
            if (fromStatus.equalsIgnoreCase(mandrillEmailStatus.status)) {
                return mandrillEmailStatus;
            }
        }

        return UNKNOWN;
    }

}