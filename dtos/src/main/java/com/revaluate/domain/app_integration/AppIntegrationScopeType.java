package com.revaluate.domain.app_integration;

import java.util.Arrays;

public enum AppIntegrationScopeType {

    IDENTIFY,
    READ,
    POST,
    CLIENT,
    ADMIN;

    public static AppIntegrationScopeType fromString(String string) throws Exception {

        return Arrays
                .stream(AppIntegrationScopeType.values())
                .filter(oauthIntegrationScopeType -> oauthIntegrationScopeType.name().toLowerCase().equals(string))
                .findFirst()
                .orElseThrow(() -> new Exception("Not found"));
    }
}