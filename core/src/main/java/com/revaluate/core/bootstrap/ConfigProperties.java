package com.revaluate.core.bootstrap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConfigProperties {

    public static final String ENVIRONMENT = "ENVIRONMENT";
    public static final String SPRING_PROFILE_ACTIVE = "spring.profiles.active";

    private boolean isProduction;

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

    @Value("${supportEmailRecipient}")
    private String supportEmailRecipient;

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

    public String getSupportEmailRecipient() {
        return supportEmailRecipient;
    }

    public void setSupportEmailRecipient(String supportEmailRecipient) {
        this.supportEmailRecipient = supportEmailRecipient;
    }

    @Override
    public String toString() {
        return "ConfigProperties{" +
                "isProduction=" + isProduction +
                ", shared='" + shared + '\'' +
                ", issuer='" + issuer + '\'' +
                ", authTokenHeaderKey='" + authTokenHeaderKey + '\'' +
                ", bearerHeaderKey='" + bearerHeaderKey + '\'' +
                ", mandrillAppKey='" + mandrillAppKey + '\'' +
                ", supportEmailRecipient='" + supportEmailRecipient + '\'' +
                '}';
    }
}
