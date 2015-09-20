package com.revaluate.app_integration.service;

import com.revaluate.app_integration.exception.AppIntegrationException;
import org.hibernate.validator.constraints.NotEmpty;

public interface AppIntegrationService {

    void createOauthIntegrationSlack(@NotEmpty String code, @NotEmpty String redirectUri, int userId) throws AppIntegrationException;
}