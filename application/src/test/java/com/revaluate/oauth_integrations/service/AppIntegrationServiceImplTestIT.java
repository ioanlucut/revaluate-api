package com.revaluate.oauth_integrations.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.core.bootstrap.ConfigProperties;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.oauth.AppIntegrationDTO;
import com.revaluate.domain.oauth.AppIntegrationType;
import com.revaluate.oauth.exception.AppIntegrationException;
import com.revaluate.oauth.persistence.AppIntegrationSlack;
import com.revaluate.oauth.persistence.AppIntegrationSlackRepository;
import com.revaluate.oauth.service.AppIntegrationServiceImpl;
import com.revaluate.resource.payment.PaymentException;
import org.dozer.DozerBeanMapper;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class AppIntegrationServiceImplTestIT extends AbstractIntegrationTests {

    @Autowired
    private AppIntegrationSlackRepository oauthIntegrationSlackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Autowired
    private ConfigProperties configProperties;

    @Test
    public void createOAuthIntegrationSlack_getAccessTokenFrom_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        AppIntegrationServiceImpl oauthIntegrationServiceMock = Mockito.spy(new AppIntegrationServiceImpl());
        prepare(oauthIntegrationServiceMock);

        //-----------------------------------------------------------------
        // Throw OauthIntegrationException
        //-----------------------------------------------------------------
        when(oauthIntegrationServiceMock.getAccessTokenFrom(anyString(), anyString())).thenThrow(new AppIntegrationException());

        exception.expect(AppIntegrationException.class);
        oauthIntegrationServiceMock.createOauthIntegrationSlack("freakyCode", "http://localhost:3000", createdUserDTO.getId());
    }

    @Test
    public void createOAuthIntegrationSlack_getIdentityOf_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        AppIntegrationServiceImpl oauthIntegrationServiceMock = Mockito.spy(new AppIntegrationServiceImpl());
        prepare(oauthIntegrationServiceMock);

        //-----------------------------------------------------------------
        // Throw OauthIntegrationException
        //-----------------------------------------------------------------
        when(oauthIntegrationServiceMock.getIdentityOf(anyString())).thenThrow(new AppIntegrationException());

        exception.expect(AppIntegrationException.class);
        oauthIntegrationServiceMock.createOauthIntegrationSlack("freakyCode", "http://localhost:3000", createdUserDTO.getId());
    }

    @Test
    public void createOAuthIntegrationSlack_getAccessTokenFromIsOk_isOk() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        AppIntegrationServiceImpl oauthIntegrationServiceMock = Mockito.spy(new AppIntegrationServiceImpl());
        prepare(oauthIntegrationServiceMock);

        //-----------------------------------------------------------------
        // Throw OauthIntegrationException
        //-----------------------------------------------------------------
        Map<String, String> map = new HashMap<>();
        map.put("access_token", "dsa");
        map.put("scope", "identify");

        Map<String, String> identityMap = new HashMap<>();
        identityMap.put("team_id", "teamId");
        identityMap.put("user_id", "userId");

        when(oauthIntegrationServiceMock.getAccessTokenFrom(anyString(), anyString())).thenReturn(map);
        when(oauthIntegrationServiceMock.getIdentityOf(anyString())).thenReturn(identityMap);
        oauthIntegrationServiceMock.createOauthIntegrationSlack("freakyCode", "http://localhost:3000", createdUserDTO.getId());

        map = new HashMap<>();
        map.put("access_token", "dsa");
        map.put("scope", "identify");

        identityMap = new HashMap<>();
        identityMap.put("team_id", "teamId2");
        identityMap.put("user_id", "userId3");
        when(oauthIntegrationServiceMock.getAccessTokenFrom(anyString(), anyString())).thenReturn(map);
        when(oauthIntegrationServiceMock.getIdentityOf(anyString())).thenReturn(identityMap);
        oauthIntegrationServiceMock.createOauthIntegrationSlack("freakyCode", "http://localhost:3000", createdUserDTO.getId());

        List<AppIntegrationSlack> allByAppIntegrationTypeAndUserId = oauthIntegrationSlackRepository.findAllByAppIntegrationTypeAndUserId(AppIntegrationType.SLACK, userDTO.getId());
        assertThat(allByAppIntegrationTypeAndUserId, is(notNullValue()));
        assertThat(allByAppIntegrationTypeAndUserId.size(), is(2));
    }

    @Test
    public void createOAuthIntegrationSlack_overridePreviousTokenForSameSlackUserIdAndTeamId_isOk() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        AppIntegrationServiceImpl oauthIntegrationServiceMock = Mockito.spy(new AppIntegrationServiceImpl());
        prepare(oauthIntegrationServiceMock);

        //-----------------------------------------------------------------
        // Throw OauthIntegrationException
        //-----------------------------------------------------------------
        Map<String, String> map = new HashMap<>();
        map.put("access_token", "dsa");
        map.put("scope", "identify");

        Map<String, String> identityMap = new HashMap<>();
        identityMap.put("team_id", "teamId");
        identityMap.put("user_id", "userId");

        when(oauthIntegrationServiceMock.getAccessTokenFrom(anyString(), anyString())).thenReturn(map);
        when(oauthIntegrationServiceMock.getIdentityOf(anyString())).thenReturn(identityMap);
        oauthIntegrationServiceMock.createOauthIntegrationSlack("freakyCode", "http://localhost:3000", createdUserDTO.getId());

        map = new HashMap<>();
        map.put("access_token", "abc");
        map.put("scope", "identify");

        identityMap = new HashMap<>();
        identityMap.put("team_id", "teamId");
        identityMap.put("user_id", "userId");
        when(oauthIntegrationServiceMock.getAccessTokenFrom(anyString(), anyString())).thenReturn(map);
        when(oauthIntegrationServiceMock.getIdentityOf(anyString())).thenReturn(identityMap);
        oauthIntegrationServiceMock.createOauthIntegrationSlack("freakyCode", "http://localhost:3000", createdUserDTO.getId());

        List<AppIntegrationSlack> allByAppIntegrationTypeAndUserId = oauthIntegrationSlackRepository.findAllByAppIntegrationTypeAndUserId(AppIntegrationType.SLACK, userDTO.getId());
        assertThat(allByAppIntegrationTypeAndUserId, is(notNullValue()));
        assertThat(allByAppIntegrationTypeAndUserId.size(), is(1));
        assertThat(allByAppIntegrationTypeAndUserId.get(0).getAccessToken(), is(equalTo("abc")));
    }

    @Test
    public void createOAuthIntegrationSlack_sameSlackUserIdForTwoUsers_isNotAllowed() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();
        UserDTO secondCreatedUserDTO = createUserDTO("a@b.c");

        AppIntegrationServiceImpl oauthIntegrationServiceMock = Mockito.spy(new AppIntegrationServiceImpl());
        prepare(oauthIntegrationServiceMock);

        //-----------------------------------------------------------------
        // Throw OauthIntegrationException
        //-----------------------------------------------------------------
        Map<String, String> map = new HashMap<>();
        map.put("access_token", "dsa");
        map.put("scope", "identify");

        Map<String, String> identityMap = new HashMap<>();
        identityMap.put("team_id", "teamId");
        identityMap.put("user_id", "userId");

        when(oauthIntegrationServiceMock.getAccessTokenFrom(anyString(), anyString())).thenReturn(map);
        when(oauthIntegrationServiceMock.getIdentityOf(anyString())).thenReturn(identityMap);
        oauthIntegrationServiceMock.createOauthIntegrationSlack("freakyCode", "http://localhost:3000", createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Try to add the same integration with another user
        //-----------------------------------------------------------------
        map = new HashMap<>();
        map.put("access_token", "abc");
        map.put("scope", "identify");

        identityMap = new HashMap<>();
        identityMap.put("team_id", "teamId");
        identityMap.put("user_id", "userId");
        when(oauthIntegrationServiceMock.getAccessTokenFrom(anyString(), anyString())).thenReturn(map);
        when(oauthIntegrationServiceMock.getIdentityOf(anyString())).thenReturn(identityMap);
        exception.expect(AppIntegrationException.class);
        oauthIntegrationServiceMock.createOauthIntegrationSlack("freakyCode", "http://localhost:3000", secondCreatedUserDTO.getId());
    }

    @Test
    public void findAllIsOk() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        AppIntegrationServiceImpl oauthIntegrationServiceMock = Mockito.spy(new AppIntegrationServiceImpl());
        prepare(oauthIntegrationServiceMock);

        //-----------------------------------------------------------------
        // Throw OauthIntegrationException
        //-----------------------------------------------------------------
        Map<String, String> map = new HashMap<>();
        map.put("access_token", "dsa");
        map.put("scope", "identify");

        Map<String, String> identityMap = new HashMap<>();
        identityMap.put("team_id", "teamId");
        identityMap.put("user_id", "userId");

        when(oauthIntegrationServiceMock.getAccessTokenFrom(anyString(), anyString())).thenReturn(map);
        when(oauthIntegrationServiceMock.getIdentityOf(anyString())).thenReturn(identityMap);
        oauthIntegrationServiceMock.createOauthIntegrationSlack("freakyCode", "http://localhost:3000", createdUserDTO.getId());

        map = new HashMap<>();
        map.put("access_token", "dsa");
        map.put("scope", "identify");

        identityMap = new HashMap<>();
        identityMap.put("team_id", "teamId2");
        identityMap.put("user_id", "userId3");
        when(oauthIntegrationServiceMock.getAccessTokenFrom(anyString(), anyString())).thenReturn(map);
        when(oauthIntegrationServiceMock.getIdentityOf(anyString())).thenReturn(identityMap);
        oauthIntegrationServiceMock.createOauthIntegrationSlack("freakyCode", "http://localhost:3000", createdUserDTO.getId());

        List<AppIntegrationDTO> allByAppIntegrationTypeAndUserId = oauthIntegrationServiceMock.findAllIntegrations(userDTO.getId());
        assertThat(allByAppIntegrationTypeAndUserId.size(), is(2));
    }

    @Test
    public void deleteWorksOk() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        AppIntegrationServiceImpl oauthIntegrationServiceMock = Mockito.spy(new AppIntegrationServiceImpl());
        prepare(oauthIntegrationServiceMock);

        //-----------------------------------------------------------------
        // Throw OauthIntegrationException
        //-----------------------------------------------------------------
        Map<String, String> map = new HashMap<>();
        map.put("access_token", "dsa");
        map.put("scope", "identify");

        Map<String, String> identityMap = new HashMap<>();
        identityMap.put("team_id", "teamId");
        identityMap.put("user_id", "userId");

        when(oauthIntegrationServiceMock.getAccessTokenFrom(anyString(), anyString())).thenReturn(map);
        when(oauthIntegrationServiceMock.getIdentityOf(anyString())).thenReturn(identityMap);
        oauthIntegrationServiceMock.createOauthIntegrationSlack("freakyCode", "http://localhost:3000", createdUserDTO.getId());

        List<AppIntegrationDTO> allByAppIntegrationTypeAndUserId = oauthIntegrationServiceMock.findAllIntegrations(userDTO.getId());
        assertThat(allByAppIntegrationTypeAndUserId.size(), is(1));

        oauthIntegrationServiceMock.removeIntegration(allByAppIntegrationTypeAndUserId.get(0).getId(), userDTO.getId());

        assertThat(oauthIntegrationServiceMock.findAllIntegrations(userDTO.getId()).size(), is(0));
    }

    private void prepare(AppIntegrationServiceImpl oauthIntegrationServiceMock) throws PaymentException {
        setFieldViaReflection(oauthIntegrationServiceMock.getClass(), oauthIntegrationServiceMock, "userRepository", userRepository);
        setFieldViaReflection(oauthIntegrationServiceMock.getClass(), oauthIntegrationServiceMock, "oauthIntegrationSlackRepository", oauthIntegrationSlackRepository);
        setFieldViaReflection(oauthIntegrationServiceMock.getClass(), oauthIntegrationServiceMock, "dozerBeanMapper", dozerBeanMapper);
        setFieldViaReflection(oauthIntegrationServiceMock.getClass(), oauthIntegrationServiceMock, "configProperties", configProperties);
    }
}