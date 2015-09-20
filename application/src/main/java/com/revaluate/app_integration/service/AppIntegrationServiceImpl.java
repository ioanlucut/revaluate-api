package com.revaluate.app_integration.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.common.base.Splitter;
import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.domain.app_integration.AppIntegrationScopeType;
import com.revaluate.domain.app_integration.AppIntegrationType;
import com.revaluate.domain.slack.SlackIdentityResponseDTO;
import com.revaluate.domain.slack.SlackTokenIssuingResponseDTO;
import com.revaluate.app_integration.exception.AppIntegrationException;
import com.revaluate.app_integration.persistence.AppIntegrationSlack;
import com.revaluate.app_integration.persistence.AppIntegrationSlackRepository;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.ClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;


@Service
@Validated
public class AppIntegrationServiceImpl implements AppIntegrationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppIntegrationServiceImpl.class);

    public static final String CLIENT_ID = "2151987168.10687444405";
    public static final String CLIENT_SECRET = "9efca5a3f6c459259e950c715c3433e2";
    public static final String SLACK_URI = "https://slack.com/";
    public static final String SLACK_ACCESS_PATH = "api/oauth.access";
    public static final String SLACK_AUTH_TEST_PATH = "api/auth.test";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppIntegrationSlackRepository oauthIntegrationSlackRepository;

    @Override
    public void createOauthIntegrationSlack(String code, String redirectUri, int userId) throws AppIntegrationException {
        User foundUser = userRepository.findOne(userId);

        try {
            SlackTokenIssuingResponseDTO accessTokenFrom = getAccessTokenFrom(code, redirectUri);
            if (StringUtils.isBlank(accessTokenFrom.getAccessToken()) || StringUtils.isBlank(accessTokenFrom.getScope())) {

                throw new AppIntegrationException("Access token could not have been retrieved");
            }

            SlackIdentityResponseDTO identityOf = getIdentityOf(accessTokenFrom.getAccessToken());
            if (StringUtils.isBlank(identityOf.getTeamId()) || StringUtils.isBlank(identityOf.getUserId())) {

                throw new AppIntegrationException("Identity could not have been retrieved");
            }

            AppIntegrationSlack appIntegrationSlack = new AppIntegrationSlack();
            appIntegrationSlack.setAppIntegrationType(AppIntegrationType.SLACK);
            appIntegrationSlack.setAccessToken(accessTokenFrom.getAccessToken());

            String allowScope = accessTokenFrom.getScope();
            Splitter splitter = Splitter.on(',').omitEmptyStrings().trimResults();
            List<String> scopeAsString = splitter.splitToList(allowScope);
            scopeAsString
                    .stream()
                    .filter(s -> AppIntegrationScopeType.CLIENT.name().toLowerCase().equals(s))
                    .findFirst()
                    .orElseThrow(() -> new AppIntegrationException("The access scope should be client"));

            appIntegrationSlack.setAppIntegrationScopeType(AppIntegrationScopeType.CLIENT);
            appIntegrationSlack.setSlackTeamId(identityOf.getTeamId());
            appIntegrationSlack.setSlackUserId(identityOf.getUserId());
            appIntegrationSlack.setUser(foundUser);

            oauthIntegrationSlackRepository.save(appIntegrationSlack);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);

            throw new AppIntegrationException("The access token / identity could not be retrieved");
        }
    }

    public SlackTokenIssuingResponseDTO getAccessTokenFrom(String code, String redirectUri) throws AppIntegrationException {
        Client client = buildClient();

        WebTarget target = client
                .target(SLACK_URI)
                .path(SLACK_ACCESS_PATH);

        Response response = target
                .queryParam("client_id", CLIENT_ID)
                .queryParam("client_secret", CLIENT_SECRET)
                .queryParam("code", code)
                .queryParam("redirect_uri", redirectUri)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
            throw new AppIntegrationException("Bad request while trying to get access token.");
        }

        Optional<SlackTokenIssuingResponseDTO> slackTokenIssuingResponseOptional = Optional
                .ofNullable(response.readEntity(SlackTokenIssuingResponseDTO.class));

        return slackTokenIssuingResponseOptional.orElseThrow(AppIntegrationException::new);
    }

    public SlackIdentityResponseDTO getIdentityOf(String token) throws AppIntegrationException {
        Client client = buildClient();

        WebTarget target = client
                .target(SLACK_URI)
                .path(SLACK_AUTH_TEST_PATH);

        Response response = target
                .queryParam("token", token)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
            throw new AppIntegrationException("Bad request while trying to get identity.");
        }

        Optional<SlackIdentityResponseDTO> slackIdentityOptional = Optional
                .ofNullable(response.readEntity(SlackIdentityResponseDTO.class));

        return slackIdentityOptional.orElseThrow(AppIntegrationException::new);
    }

    private Client buildClient() {
        final JacksonJsonProvider jacksonJsonProvider = new JacksonJaxbJsonProvider().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return ClientBuilder.newClient(new ClientConfig(jacksonJsonProvider));
    }
}