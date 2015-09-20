package com.revaluate.integrations.service;

import com.revaluate.integrations.exception.OauthIntegrationException;
import org.hibernate.validator.constraints.NotEmpty;

public interface OauthIntegrationService {

    void createOauthIntegrationSlack(@NotEmpty String code, @NotEmpty String redirectUri, int userId) throws OauthIntegrationException;
}