package com.revaluate.oauth_integrations.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.domain.oauth.AppIntegrationType;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.slack.SlackIdentityResponseDTOBuilder;
import com.revaluate.domain.slack.SlackTokenIssuingResponseDTOBuilder;
import com.revaluate.oauth.exception.AppIntegrationException;
import com.revaluate.oauth.persistence.AppIntegrationSlack;
import com.revaluate.oauth.persistence.AppIntegrationSlackRepository;
import com.revaluate.oauth.service.AppIntegrationServiceImpl;
import com.revaluate.resource.payment.PaymentException;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class AppIntegrationServiceImplTestIT extends AbstractIntegrationTests {

    @Autowired
    private AppIntegrationSlackRepository oauthIntegrationSlackRepository;

    @Autowired
    private UserRepository userRepository;

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
        when(oauthIntegrationServiceMock.getAccessTokenFrom(anyString(), anyString())).thenReturn(new SlackTokenIssuingResponseDTOBuilder().withAccessToken("dsa").withScope("client").build());
        when(oauthIntegrationServiceMock.getIdentityOf(anyString())).thenReturn(new SlackIdentityResponseDTOBuilder().withOk("ok").withTeamId("teamId").withUserId("userId").build());
        oauthIntegrationServiceMock.createOauthIntegrationSlack("freakyCode", "http://localhost:3000", createdUserDTO.getId());

        when(oauthIntegrationServiceMock.getAccessTokenFrom(anyString(), anyString())).thenReturn(new SlackTokenIssuingResponseDTOBuilder().withAccessToken("dsa").withScope("client").build());
        when(oauthIntegrationServiceMock.getIdentityOf(anyString())).thenReturn(new SlackIdentityResponseDTOBuilder().withOk("ok").withTeamId("teamId2").withUserId("userId3").build());
        oauthIntegrationServiceMock.createOauthIntegrationSlack("freakyCode", "http://localhost:3000", createdUserDTO.getId());

        List<AppIntegrationSlack> allByAppIntegrationTypeAndUserId = oauthIntegrationSlackRepository.findAllByAppIntegrationTypeAndUserId(AppIntegrationType.SLACK, userDTO.getId());
        assertThat(allByAppIntegrationTypeAndUserId, is(notNullValue()));
        assertThat(allByAppIntegrationTypeAndUserId.size(), is(2));
    }

    private void prepare(AppIntegrationServiceImpl oauthIntegrationServiceMock) throws PaymentException {
        setFieldViaReflection(oauthIntegrationServiceMock.getClass(), oauthIntegrationServiceMock, "userRepository", userRepository);
        setFieldViaReflection(oauthIntegrationServiceMock.getClass(), oauthIntegrationServiceMock, "oauthIntegrationSlackRepository", oauthIntegrationSlackRepository);
    }
}