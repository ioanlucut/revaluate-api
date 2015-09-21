package com.revaluate.oauth.service;

import com.revaluate.oauth.exception.AppIntegrationException;
import org.hibernate.validator.constraints.NotEmpty;

public interface AppIntegrationService {

    void createOauthIntegrationSlack(@NotEmpty String code, @NotEmpty String redirectUri, int userId) throws AppIntegrationException;
}