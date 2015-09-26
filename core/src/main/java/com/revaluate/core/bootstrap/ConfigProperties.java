package com.revaluate.core.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ConfigProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigProperties.class);

    public static final String ENVIRONMENT = "ENVIRONMENT";
    public static final String SPRING_PROFILE_ACTIVE = "spring.profiles.active";

    @PostConstruct
    public void afterInject() {
        LOGGER.info("Configuration loaded: ");
    }

    private boolean production;

    @Value("${websiteHost}")
    private String websiteHost;

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

    /**
     * The URL where the reset password link will be set in the email.
     */
    @Value("${resetPasswordURLFormat}")
    private String resetPasswordURLFormat;

    /**
     * The URL where the confirm email link will be set in the email.
     */
    @Value("${confirmEmailURLFormat}")
    private String confirmEmailURLFormat;

    @Value("${braintreePlanId}")
    private String braintreePlanId;

    @Value("${braintreeMerchantId}")
    private String braintreeMerchantId;

    @Value("${braintreePublicKey}")
    private String braintreePublicKey;

    @Value("${braintreePrivateKey}")
    private String braintreePrivateKey;

    @Value("${slackClientId}")
    private String slackClientId;

    @Value("${slackClientSecret}")
    private String slackClientSecret;

    @Value("${intercomAppId}")
    private String intercomAppId;

    @Value("${intercomAppKey}")
    private String intercomAppKey;

    public boolean isProduction() {
        return production;
    }

    @Value("${isProduction}")
    public void setProduction(boolean isProduction) {
        this.production = isProduction;
    }

    public String getWebsiteHost() {
        return websiteHost;
    }

    public void setWebsiteHost(String websiteHost) {
        this.websiteHost = websiteHost;
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

    public String getResetPasswordURLFormat() {
        return resetPasswordURLFormat;
    }

    public void setResetPasswordURLFormat(String resetPasswordURLFormat) {
        this.resetPasswordURLFormat = resetPasswordURLFormat;
    }

    public String getConfirmEmailURLFormat() {
        return confirmEmailURLFormat;
    }

    public void setConfirmEmailURLFormat(String confirmEmailURLFormat) {
        this.confirmEmailURLFormat = confirmEmailURLFormat;
    }

    public String getBraintreePlanId() {
        return braintreePlanId;
    }

    public void setBraintreePlanId(String braintreePlanId) {
        this.braintreePlanId = braintreePlanId;
    }

    public String getBraintreeMerchantId() {
        return braintreeMerchantId;
    }

    public void setBraintreeMerchantId(String braintreeMerchantId) {
        this.braintreeMerchantId = braintreeMerchantId;
    }

    public String getBraintreePublicKey() {
        return braintreePublicKey;
    }

    public void setBraintreePublicKey(String braintreePublicKey) {
        this.braintreePublicKey = braintreePublicKey;
    }

    public String getBraintreePrivateKey() {
        return braintreePrivateKey;
    }

    public void setBraintreePrivateKey(String braintreePrivateKey) {
        this.braintreePrivateKey = braintreePrivateKey;
    }

    public String getSlackClientId() {
        return slackClientId;
    }

    public void setSlackClientId(String slackClientId) {
        this.slackClientId = slackClientId;
    }

    public String getSlackClientSecret() {
        return slackClientSecret;
    }

    public void setSlackClientSecret(String slackClientSecret) {
        this.slackClientSecret = slackClientSecret;
    }

    public String getIntercomAppId() {
        return intercomAppId;
    }

    public void setIntercomAppId(String intercomAppId) {
        this.intercomAppId = intercomAppId;
    }

    public String getIntercomAppKey() {
        return intercomAppKey;
    }

    public void setIntercomAppKey(String intercomAppKey) {
        this.intercomAppKey = intercomAppKey;
    }

    @Override
    public String toString() {
        return "ConfigProperties{" +
                "production=" + production +
                ", websiteHost='" + websiteHost + '\'' +
                ", skipSendEmail=" + skipSendEmail +
                ", shared='" + shared + '\'' +
                ", issuer='" + issuer + '\'' +
                ", authTokenHeaderKey='" + authTokenHeaderKey + '\'' +
                ", bearerHeaderKey='" + bearerHeaderKey + '\'' +
                ", mandrillAppKey='" + mandrillAppKey + '\'' +
                ", replyEmailRecipient='" + replyEmailRecipient + '\'' +
                ", noReplyEmailRecipient='" + noReplyEmailRecipient + '\'' +
                ", commonEmailsRecipient='" + commonEmailsRecipient + '\'' +
                ", resetPasswordURLFormat='" + resetPasswordURLFormat + '\'' +
                ", confirmEmailURLFormat='" + confirmEmailURLFormat + '\'' +
                ", braintreePlanId='" + braintreePlanId + '\'' +
                ", braintreeMerchantId='" + braintreeMerchantId + '\'' +
                ", braintreePublicKey='" + braintreePublicKey + '\'' +
                ", braintreePrivateKey='" + braintreePrivateKey + '\'' +
                ", slackClientId='" + slackClientId + '\'' +
                ", slackClientSecret='" + slackClientSecret + '\'' +
                ", intercomAppId='" + intercomAppId + '\'' +
                ", intercomAppKey='" + intercomAppKey + '\'' +
                '}';
    }
}
