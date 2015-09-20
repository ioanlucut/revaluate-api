package com.revaluate.integrations.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.domain.account.OauthIntegrationType;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.slack.SlackIdentityResponseDTOBuilder;
import com.revaluate.domain.slack.SlackTokenIssuingResponseDTOBuilder;
import com.revaluate.integrations.exception.OauthIntegrationException;
import com.revaluate.integrations.persistence.OauthIntegrationSlack;
import com.revaluate.integrations.persistence.OauthIntegrationSlackRepository;
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

public class OAuthIntegrationServiceImplTestIT extends AbstractIntegrationTests {

    @Autowired
    private OauthIntegrationSlackRepository oauthIntegrationSlackRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void createOAuthIntegrationSlack_getAccessTokenFrom_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        OauthIntegrationServiceImpl oauthIntegrationServiceMock = Mockito.spy(new OauthIntegrationServiceImpl());
        prepare(oauthIntegrationServiceMock);

        //-----------------------------------------------------------------
        // Throw OauthIntegrationException
        //-----------------------------------------------------------------
        when(oauthIntegrationServiceMock.getAccessTokenFrom(anyString(), anyString())).thenThrow(new OauthIntegrationException());

        exception.expect(OauthIntegrationException.class);
        oauthIntegrationServiceMock.createOauthIntegrationSlack("freakyCode", "http://localhost:3000", createdUserDTO.getId());
    }

    @Test
    public void createOAuthIntegrationSlack_getIdentityOf_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        OauthIntegrationServiceImpl oauthIntegrationServiceMock = Mockito.spy(new OauthIntegrationServiceImpl());
        prepare(oauthIntegrationServiceMock);

        //-----------------------------------------------------------------
        // Throw OauthIntegrationException
        //-----------------------------------------------------------------
        when(oauthIntegrationServiceMock.getIdentityOf(anyString())).thenThrow(new OauthIntegrationException());

        exception.expect(OauthIntegrationException.class);
        oauthIntegrationServiceMock.createOauthIntegrationSlack("freakyCode", "http://localhost:3000", createdUserDTO.getId());
    }

    @Test
    public void createOAuthIntegrationSlack_getAccessTokenFromIsOk_isOk() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        OauthIntegrationServiceImpl oauthIntegrationServiceMock = Mockito.spy(new OauthIntegrationServiceImpl());
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

        List<OauthIntegrationSlack> allByOauthIntegrationTypeAndUserId = oauthIntegrationSlackRepository.findAllByOauthIntegrationTypeAndUserId(OauthIntegrationType.SLACK, userDTO.getId());
        assertThat(allByOauthIntegrationTypeAndUserId, is(notNullValue()));
        assertThat(allByOauthIntegrationTypeAndUserId.size(), is(2));
    }

    private void prepare(OauthIntegrationServiceImpl oauthIntegrationServiceMock) throws PaymentException {
        setFieldViaReflection(oauthIntegrationServiceMock.getClass(), oauthIntegrationServiceMock, "userRepository", userRepository);
        setFieldViaReflection(oauthIntegrationServiceMock.getClass(), oauthIntegrationServiceMock, "oauthIntegrationSlackRepository", oauthIntegrationSlackRepository);
    }
}