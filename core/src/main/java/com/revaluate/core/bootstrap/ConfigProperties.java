package com.revaluate.core.bootstrap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConfigProperties {

    public static final String ENVIRONMENT = "ENVIRONMENT";
    public static final String SPRING_PROFILE_ACTIVE = "spring.profiles.active";

    private boolean isProduction;

    @Value("${skipSendEmail}")
    private boolean skipSendEmail;

    @Value("${shared}")
    private String shared;

    @Value("${issuer}")
    private String issuer;

    @Value("${authTokenHeaderKey}")
    private String authTokenHeaderKey;

    @Value("${bearerHeaderKey}")
    private String bearerHeaderKey;

    @Value("${mandrillAppKey}")
    private String mandrillAppKey;

    @Value("${replyEmailRecipient}")
    private String replyEmailRecipient;

    @Value("${noReplyEmailRecipient}")
    private String noReplyEmailRecipient;

    /**
     * If emails are sent from dev environment,
     * they are not sent to the end user.
     */
    @Value("${commonEmailsRecipient}")
    private String commonEmailsRecipient;

    @Value("${braintreePlanId}")
    private String braintreePlanId;

    public boolean isProduction() {
        return isProduction;
    }

    @Value("${isProduction}")
    public void setProduction(boolean isProduction) {
        this.isProduction = isProduction;
    }

    public String getShared() {
        return shared;
    }

    public void setShared(String shared) {
        this.shared = shared;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getAuthTokenHeaderKey() {
        return authTokenHeaderKey;
    }

    public void setAuthTokenHeaderKey(String authTokenHeaderKey) {
        this.authTokenHeaderKey = authTokenHeaderKey;
    }

    public String getBearerHeaderKey() {
        return bearerHeaderKey;
    }

    public void setBearerHeaderKey(String bearerHeaderKey) {
        this.bearerHeaderKey = bearerHeaderKey;
    }

    public String getMandrillAppKey() {
        return mandrillAppKey;
    }

    public void setMandrillAppKey(String mandrillAppKey) {
        this.mandrillAppKey = mandrillAppKey;
    }

    public String getReplyEmailRecipient() {
        return replyEmailRecipient;
    }

    public void setReplyEmailRecipient(String replyEmailRecipient) {
        this.replyEmailRecipient = replyEmailRecipient;
    }

    public String getNoReplyEmailRecipient() {
        return noReplyEmailRecipient;
    }

    public void setNoReplyEmailRecipient(String noReplyEmailRecipient) {
        this.noReplyEmailRecipient = noReplyEmailRecipient;
    }

    public String getCommonEmailsRecipient() {
        return commonEmailsRecipient;
    }

    public void setCommonEmailsRecipient(String commonEmailsRecipient) {
        this.commonEmailsRecipient = commonEmailsRecipient;
    }

    public boolean isSkipSendEmail() {
        return skipSendEmail;
    }

    public void setSkipSendEmail(boolean skipSendEmail) {
        this.skipSendEmail = skipSendEmail;
    }

    public String getBraintreePlanId() {
        return braintreePlanId;
    }

    public void setBraintreePlanId(String braintreePlanId) {
        this.braintreePlanId = braintreePlanId;
    }

    @Override
    public String toString() {
        return "ConfigProperties{" +
                "isProduction=" + isProduction +
                ", skipSendEmail=" + skipSendEmail +
                ", shared='" + shared + '\'' +
                ", issuer='" + issuer + '\'' +
                ", authTokenHeaderKey='" + authTokenHeaderKey + '\'' +
                ", bearerHeaderKey='" + bearerHeaderKey + '\'' +
                ", mandrillAppKey='" + mandrillAppKey + '\'' +
                ", replyEmailRecipient='" + replyEmailRecipient + '\'' +
                ", noReplyEmailRecipient='" + noReplyEmailRecipient + '\'' +
                ", commonEmailsRecipient='" + commonEmailsRecipient + '\'' +
                '}';
    }
}
