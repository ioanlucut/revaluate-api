package com.revaluate.oauth_integr.service;

import com.revaluate.oauth_integr.exception.OauthIntegrationException;
import org.hibernate.validator.constraints.NotEmpty;

public interface OauthIntegrationService {

    void createOauthIntegrationSlack(@NotEmpty String code, @NotEmpty String redirectUri, int userId) throws OauthIntegrationException;
}