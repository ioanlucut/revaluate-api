package com.revaluate.domain.oauth;

import java.util.Arrays;

public enum OauthIntegrationScopeType {

    IDENTIFY,
    READ,
    POST,
    CLIENT,
    ADMIN;

    public static OauthIntegrationScopeType fromString(String string) throws Exception {

        return Arrays
                .stream(OauthIntegrationScopeType.values())
                .filter(oauthIntegrationScopeType -> oauthIntegrationScopeType.name().toLowerCase().equals(string))
                .findFirst()
                .orElseThrow(() -> new Exception("Not found"));
    }
}