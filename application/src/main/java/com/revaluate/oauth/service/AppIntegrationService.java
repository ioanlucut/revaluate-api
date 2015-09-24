package com.revaluate.oauth.service;

import com.revaluate.domain.oauth.AppIntegrationDTO;
import com.revaluate.oauth.exception.AppIntegrationException;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface AppIntegrationService {

    @NotNull
    AppIntegrationDTO createOauthIntegrationSlack(@NotEmpty String code, @NotEmpty String redirectUri, int userId) throws AppIntegrationException;

    @NotNull
    List<AppIntegrationDTO> findAllIntegrations(int userId) throws AppIntegrationException;

    void removeIntegration(@Min(1) int appIntegrationId, int userId) throws AppIntegrationException;
}