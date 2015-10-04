package com.revaluate.oauth.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.revaluate.core.bootstrap.ConfigProperties;
import com.revaluate.domain.oauth.AppIntegrationType;
import com.revaluate.oauth.exception.AppIntegrationException;
import org.apache.commons.lang3.StringUtils;
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
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Optional;


@Service
@Validated
public class AppIntegrationGranterServiceImpl implements AppIntegrationGranterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppIntegrationGranterServiceImpl.class);
    private static final String URI_FORMAT = "%s#state=%s&access_token=%s";
    private static final String UTF_8 = "UTF-8";

    @Autowired
    private ConfigProperties configProperties;

    //[REDIRECT_PATH]#state=[STATE]&access_token=ABCD1233234&expires=123123123
    @Override
    public URI grantOauthIntegration(String code, String state) throws AppIntegrationException {
        JsonObject stateJsonObject = tryToParseStateAsJson(state);
        String network = Optional
                .ofNullable(stateJsonObject.get("network"))
                .orElseThrow(() -> new AppIntegrationException("Network information is missing")).getAsString();

        if (!AppIntegrationType.SLACK.name().equalsIgnoreCase(network)) {
            throw new AppIntegrationException(String.format("Network %s not supported", network));
        }

        String redirectUri = Optional
                .ofNullable(stateJsonObject.get("redirect_uri"))
                .orElseThrow(() -> new AppIntegrationException("Redirect URI is missing")).getAsString();

        Map<String, String> accessTokenFrom = getAccessTokenFrom(code, redirectUri);
        String accessToken = accessTokenFrom.get("access_token");
        String scope = accessTokenFrom.get("scope");

        if (StringUtils.isBlank(accessToken) || StringUtils.isBlank(scope)) {

            throw new AppIntegrationException(String.format("Access token or scope not present in the response. Response %s", accessTokenFrom));
        }
        try {
            return new URI(String.format(URI_FORMAT, redirectUri, URLEncoder.encode(state, UTF_8), URLEncoder.encode(accessToken, UTF_8)));
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);

            throw new AppIntegrationException("Server error");
        }
    }

    public JsonObject tryToParseStateAsJson(String state) throws AppIntegrationException {
        try {
            return new JsonParser().parse(state).getAsJsonObject();
        } catch (IllegalStateException ex) {
            LOGGER.error(ex.getMessage(), ex);

            throw new AppIntegrationException(String.format("Could not parse %s as json", state));
        }
    }

    Map<String, String> getAccessTokenFrom(String code, String redirectUri) throws AppIntegrationException {
        Client client = ClientBuilder.newClient();

        WebTarget target = client
                .target(SlackUtils.SLACK_URI)
                .path(SlackUtils.SLACK_ACCESS_PATH);

        Response response = target
                .queryParam(SlackUtils.CLIENT_ID, configProperties.getSlackClientId())
                .queryParam(SlackUtils.CLIENT_SECRET, configProperties.getSlackClientSecret())
                .queryParam(SlackUtils.CODE, code)
                .queryParam(SlackUtils.REDIRECT_URI, redirectUri)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
            throw new AppIntegrationException("Bad request while trying to get access token.");
        }

        String asString = response.readEntity(String.class);
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();

        return gson.fromJson(asString, type);
    }


}