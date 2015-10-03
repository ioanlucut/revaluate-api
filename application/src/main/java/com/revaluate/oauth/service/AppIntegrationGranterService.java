package com.revaluate.oauth.service;

import com.revaluate.oauth.exception.AppIntegrationException;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.net.URI;

public interface AppIntegrationGranterService {

    @NotNull
    URI grantOauthIntegration(@NotEmpty String code, @NotEmpty String state) throws AppIntegrationException;
}