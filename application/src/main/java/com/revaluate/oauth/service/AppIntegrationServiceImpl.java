package com.revaluate.oauth.service;

import com.google.common.base.Splitter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.core.bootstrap.ConfigProperties;
import com.revaluate.domain.oauth.AppIntegrationDTO;
import com.revaluate.domain.oauth.AppIntegrationScopeType;
import com.revaluate.domain.oauth.AppIntegrationType;
import com.revaluate.oauth.exception.AppIntegrationException;
import com.revaluate.oauth.persistence.AppIntegrationSlack;
import com.revaluate.oauth.persistence.AppIntegrationSlackRepository;
import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Validated
public class AppIntegrationServiceImpl implements AppIntegrationService {

    //-----------------------------------------------------------------
    // SLACK endpoints and methods
    //-----------------------------------------------------------------
    public static final String SLACK_URI = "https://slack.com/";
    public static final String SLACK_ACCESS_PATH = "api/oauth.access";
    public static final String SLACK_AUTH_TEST_PATH = "api/auth.test";

    //-----------------------------------------------------------------
    // QUERY params
    //-----------------------------------------------------------------
    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String CODE = "code";
    public static final String REDIRECT_URI = "redirect_uri";
    public static final String TOKEN = "token";

    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppIntegrationSlackRepository oauthIntegrationSlackRepository;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Override
    public AppIntegrationDTO createOauthIntegrationSlack(String code, String redirectUri, int userId) throws AppIntegrationException {
        User foundUser = userRepository.findOne(userId);

        Map<String, String> accessTokenFrom = getAccessTokenFrom(code, redirectUri);
        String accessToken = accessTokenFrom.get("access_token");
        String scope = accessTokenFrom.get("scope");

        if (StringUtils.isBlank(accessToken) || StringUtils.isBlank(scope)) {

            throw new AppIntegrationException(String.format("Access token or scope not present in the response. Response %s", accessTokenFrom));
        }

        Map<String, String> identityOf = getIdentityOf(accessToken);
        String identityTeamId = identityOf.get("team_id");
        String identityUserId = identityOf.get("user_id");
        if (StringUtils.isBlank(identityTeamId) || StringUtils.isBlank(identityUserId)) {

            throw new AppIntegrationException(String.format("Identity could not have been retrieved. Response %s", identityOf));
        }

        //-----------------------------------------------------------------
        // Override existing
        //-----------------------------------------------------------------

        long slackUserIdAndTeamIdIsTaken = oauthIntegrationSlackRepository
                .countByAppIntegrationTypeAndSlackUserIdAndSlackTeamIdAndUserIdNot(AppIntegrationType.SLACK, identityUserId, identityTeamId, userId);

        //-----------------------------------------------------------------
        // There is already a user registered with this slack user id & team id
        // The relationship is 1 to many (user to many slack accounts)
        // and not same slack account to many users.
        //-----------------------------------------------------------------
        if (slackUserIdAndTeamIdIsTaken > 0) {
            throw new AppIntegrationException("The user id and team id is already used by another user.");
        }

        AppIntegrationSlack appIntegrationSlack = oauthIntegrationSlackRepository
                .findOneByAppIntegrationTypeAndSlackUserIdAndSlackTeamIdAndUserId(AppIntegrationType.SLACK, identityUserId, identityTeamId, userId)
                .orElseGet(AppIntegrationSlack::new);

        appIntegrationSlack.setAppIntegrationType(AppIntegrationType.SLACK);
        appIntegrationSlack.setAccessToken(accessToken);

        Splitter splitter = Splitter.on(',').omitEmptyStrings().trimResults();
        List<String> scopeAsString = splitter.splitToList(scope);
        scopeAsString
                .stream()
                .filter(s -> AppIntegrationScopeType.CLIENT.name().toLowerCase().equals(s))
                .findFirst()
                .orElseThrow(() -> new AppIntegrationException("The access scope should be client"));

        appIntegrationSlack.setAppIntegrationScopeType(AppIntegrationScopeType.CLIENT);
        appIntegrationSlack.setSlackTeamId(identityTeamId);
        appIntegrationSlack.setSlackUserId(identityUserId);
        appIntegrationSlack.setUser(foundUser);


        return dozerBeanMapper.map(oauthIntegrationSlackRepository.save(appIntegrationSlack), AppIntegrationDTO.class);
    }

    public Map<String, String> getAccessTokenFrom(String code, String redirectUri) throws AppIntegrationException {
        Client client = buildClient();

        WebTarget target = client
                .target(SLACK_URI)
                .path(SLACK_ACCESS_PATH);

        Response response = target
                .queryParam(CLIENT_ID, configProperties.getSlackClientId())
                .queryParam(CLIENT_SECRET, configProperties.getSlackClientSecret())
                .queryParam(CODE, code)
                .queryParam(REDIRECT_URI, redirectUri)
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

    public Map<String, String> getIdentityOf(String token) throws AppIntegrationException {
        Client client = buildClient();

        WebTarget target = client
                .target(SLACK_URI)
                .path(SLACK_AUTH_TEST_PATH);

        Response response = target
                .queryParam(TOKEN, token)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
            throw new AppIntegrationException("Bad request while trying to get identity.");
        }

        String asString = response.readEntity(String.class);
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();

        return gson.fromJson(asString, type);
    }

    private Client buildClient() {

        return ClientBuilder.newClient();
    }

    @Override
    public List<AppIntegrationDTO> findAllIntegrations(int userId) throws AppIntegrationException {

        return collectAndGet(oauthIntegrationSlackRepository.findAllByAppIntegrationTypeAndUserId(AppIntegrationType.SLACK, userId));
    }

    @Override
    public void removeIntegration(int appIntegrationId, int userId) throws AppIntegrationException {

        oauthIntegrationSlackRepository.deleteAllByAppIntegrationIdAndUserId(appIntegrationId, userId);
    }

    private List<AppIntegrationDTO> collectAndGet(List<AppIntegrationSlack> appIntegrations) {
        return appIntegrations
                .stream()
                .map(category -> dozerBeanMapper.map(category, AppIntegrationDTO.class))
                .collect(Collectors.toList());
    }
}