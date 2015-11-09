package com.revaluate.oauth.service;

import com.revaluate.domain.oauth.AppIntegrationDTO;
import com.revaluate.domain.oauth.AppSlackIntegrationDTO;
import com.revaluate.oauth.exception.AppIntegrationException;
import net.bull.javamelody.MonitoredWithSpring;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@MonitoredWithSpring
public interface AppIntegrationService {

    @NotNull
    AppIntegrationDTO createOauthIntegrationSlack(@NotNull @Valid AppSlackIntegrationDTO appSlackIntegrationDTO, int userId) throws AppIntegrationException;

    @NotNull
    List<AppIntegrationDTO> findAllIntegrations(int userId) throws AppIntegrationException;

    void removeIntegration(@Min(1) int appIntegrationId, int userId) throws AppIntegrationException;
}