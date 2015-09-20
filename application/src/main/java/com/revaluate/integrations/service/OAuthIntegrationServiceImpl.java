package com.revaluate.integrations.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.common.base.Splitter;
import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.domain.account.OauthIntegrationScopeType;
import com.revaluate.domain.account.OauthIntegrationType;
import com.revaluate.domain.slack.SlackIdentityResponseDTO;
import com.revaluate.domain.slack.SlackTokenIssuingResponseDTO;
import com.revaluate.integrations.exception.OauthIntegrationException;
import com.revaluate.integrations.persistence.OauthIntegrationSlack;
import com.revaluate.integrations.persistence.OauthIntegrationSlackRepository;
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
public class OauthIntegrationServiceImpl implements OauthIntegrationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OauthIntegrationServiceImpl.class);

    public static final String CLIENT_ID = "2151987168.10687444405";
    public static final String CLIENT_SECRET = "9efca5a3f6c459259e950c715c3433e2";
    public static final String SLACK_URI = "https://slack.com/";
    public static final String SLACK_ACCESS_PATH = "api/oauth.access";
    public static final String SLACK_AUTH_TEST_PATH = "api/auth.test";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OauthIntegrationSlackRepository oauthIntegrationSlackRepository;

    @Override
    public void createOauthIntegrationSlack(String code, String redirectUri, int userId) throws OauthIntegrationException {
        User foundUser = userRepository.findOne(userId);

        try {
            SlackTokenIssuingResponseDTO accessTokenFrom = getAccessTokenFrom(code, redirectUri);
            if (StringUtils.isBlank(accessTokenFrom.getAccessToken()) || StringUtils.isBlank(accessTokenFrom.getScope())) {

                throw new OauthIntegrationException("Access token could not have been retrieved");
            }

            SlackIdentityResponseDTO identityOf = getIdentityOf(accessTokenFrom.getAccessToken());
            if (StringUtils.isBlank(identityOf.getTeamId()) || StringUtils.isBlank(identityOf.getUserId())) {

                throw new OauthIntegrationException("Identity could not have been retrieved");
            }

            OauthIntegrationSlack oAuthIntegrationSlack = new OauthIntegrationSlack();
            oAuthIntegrationSlack.setOauthIntegrationType(OauthIntegrationType.SLACK);
            oAuthIntegrationSlack.setAccessToken(accessTokenFrom.getAccessToken());

            String allowScope = accessTokenFrom.getScope();
            Splitter splitter = Splitter.on(',').omitEmptyStrings().trimResults();
            List<String> scopeAsString = splitter.splitToList(allowScope);
            scopeAsString
                    .stream()
                    .filter(s -> OauthIntegrationScopeType.CLIENT.name().toLowerCase().equals(s))
                    .findFirst()
                    .orElseThrow(() -> new OauthIntegrationException("The access scope should be client"));

            oAuthIntegrationSlack.setOauthIntegrationScopeType(OauthIntegrationScopeType.CLIENT);
            oAuthIntegrationSlack.setSlackTeamId(identityOf.getTeamId());
            oAuthIntegrationSlack.setSlackUserId(identityOf.getUserId());
            oAuthIntegrationSlack.setUser(foundUser);

            oauthIntegrationSlackRepository.save(oAuthIntegrationSlack);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);

            throw new OauthIntegrationException("The access token / identity could not be retrieved");
        }
    }

    public SlackTokenIssuingResponseDTO getAccessTokenFrom(String code, String redirectUri) throws OauthIntegrationException {
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
            throw new OauthIntegrationException("Bad request while trying to get access token.");
        }

        Optional<SlackTokenIssuingResponseDTO> slackTokenIssuingResponseOptional = Optional
                .ofNullable(response.readEntity(SlackTokenIssuingResponseDTO.class));

        return slackTokenIssuingResponseOptional.orElseThrow(OauthIntegrationException::new);
    }

    public SlackIdentityResponseDTO getIdentityOf(String token) throws OauthIntegrationException {
        Client client = buildClient();

        WebTarget target = client
                .target(SLACK_URI)
                .path(SLACK_AUTH_TEST_PATH);

        Response response = target
                .queryParam("token", token)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
            throw new OauthIntegrationException("Bad request while trying to get identity.");
        }

        Optional<SlackIdentityResponseDTO> slackIdentityOptional = Optional
                .ofNullable(response.readEntity(SlackIdentityResponseDTO.class));

        return slackIdentityOptional.orElseThrow(OauthIntegrationException::new);
    }

    private Client buildClient() {
        final JacksonJsonProvider jacksonJsonProvider = new JacksonJaxbJsonProvider().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return ClientBuilder.newClient(new ClientConfig(jacksonJsonProvider));
    }
}