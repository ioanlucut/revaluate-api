package com.revaluate.oauth_integrations.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.oauth.AppIntegrationDTO;
import com.revaluate.domain.oauth.AppIntegrationType;
import com.revaluate.domain.oauth.AppSlackIntegrationDTO;
import com.revaluate.domain.oauth.AppSlackIntegrationDTOBuilder;
import com.revaluate.oauth.exception.AppIntegrationException;
import com.revaluate.oauth.persistence.AppIntegrationSlack;
import com.revaluate.oauth.persistence.AppIntegrationSlackRepository;
import com.revaluate.oauth.service.AppIntegrationServiceImpl;
import com.revaluate.resource.payment.PaymentException;
import org.dozer.DozerBeanMapper;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AppIntegrationServiceImplTestIT extends AbstractIntegrationTests {

    @Autowired
    private AppIntegrationSlackRepository oauthIntegrationSlackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

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

        AppSlackIntegrationDTO appSlackIntegrationDTO = new AppSlackIntegrationDTOBuilder()
                .withAccessToken("accessTokenDummyValue")
                .withScopes("identify,basic")
                .withTeamId("nofameTeamId")
                .withTeamName("yahoo")
                .withUserId("U23UserId")
                .build();
        oauthIntegrationServiceMock.createOauthIntegrationSlack(appSlackIntegrationDTO, createdUserDTO.getId());

        appSlackIntegrationDTO = new AppSlackIntegrationDTOBuilder()
                .withAccessToken("accessTokenDummyValue")
                .withScopes("identify,basic")
                .withTeamId("nofameTeamId2")
                .withTeamName("yahoo")
                .withUserId("U23UserId2")
                .build();

        oauthIntegrationServiceMock.createOauthIntegrationSlack(appSlackIntegrationDTO, createdUserDTO.getId());

        List<AppIntegrationSlack> allByAppIntegrationTypeAndUserId = oauthIntegrationSlackRepository.findAllByAppIntegrationTypeAndUserId(AppIntegrationType.SLACK, userDTO.getId());
        assertThat(allByAppIntegrationTypeAndUserId).isNotNull();
        assertThat(allByAppIntegrationTypeAndUserId.size()).isEqualTo((2));
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
        AppSlackIntegrationDTO appSlackIntegrationDTO = new AppSlackIntegrationDTOBuilder()
                .withAccessToken("accessTokenDummyValue")
                .withScopes("identify,basic")
                .withTeamId("nofameTeamId")
                .withTeamName("yahoo")
                .withUserId("U23UserId")
                .build();
        oauthIntegrationServiceMock.createOauthIntegrationSlack(appSlackIntegrationDTO, createdUserDTO.getId());

        appSlackIntegrationDTO = new AppSlackIntegrationDTOBuilder()
                .withAccessToken("accessTokenDummyValueTwo")
                .withScopes("identify,basic")
                .withTeamId("nofameTeamId")
                .withTeamName("yahoo")
                .withUserId("U23UserId")
                .build();

        oauthIntegrationServiceMock.createOauthIntegrationSlack(appSlackIntegrationDTO, createdUserDTO.getId());

        List<AppIntegrationSlack> allByAppIntegrationTypeAndUserId = oauthIntegrationSlackRepository.findAllByAppIntegrationTypeAndUserId(AppIntegrationType.SLACK, userDTO.getId());
        assertThat(allByAppIntegrationTypeAndUserId).isNotNull();
        assertThat(allByAppIntegrationTypeAndUserId.size()).isEqualTo((1));
        assertThat(allByAppIntegrationTypeAndUserId.get(0).getAccessToken()).isEqualTo("accessTokenDummyValueTwo");
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
        AppSlackIntegrationDTO appSlackIntegrationDTO = new AppSlackIntegrationDTOBuilder()
                .withAccessToken("accessTokenDummyValue")
                .withScopes("identify,basic")
                .withTeamId("nofameTeamId")
                .withTeamName("yahoo")
                .withUserId("U23UserId")
                .build();
        oauthIntegrationServiceMock.createOauthIntegrationSlack(appSlackIntegrationDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Try to add the same integration with another user
        //-----------------------------------------------------------------
        exception.expect(AppIntegrationException.class);
        oauthIntegrationServiceMock.createOauthIntegrationSlack(appSlackIntegrationDTO, secondCreatedUserDTO.getId());
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

        AppSlackIntegrationDTO appSlackIntegrationDTO = new AppSlackIntegrationDTOBuilder()
                .withAccessToken("accessTokenDummyValue")
                .withScopes("identify,basic")
                .withTeamId("nofameTeamId")
                .withTeamName("yahoo")
                .withUserId("U23UserId")
                .build();
        oauthIntegrationServiceMock.createOauthIntegrationSlack(appSlackIntegrationDTO, createdUserDTO.getId());

        appSlackIntegrationDTO = new AppSlackIntegrationDTOBuilder()
                .withAccessToken("accessTokenDummyValue")
                .withScopes("identify,basic")
                .withTeamId("nofameTeamId2")
                .withTeamName("yahoo")
                .withUserId("U23UserId2")
                .build();

        oauthIntegrationServiceMock.createOauthIntegrationSlack(appSlackIntegrationDTO, createdUserDTO.getId());

        List<AppIntegrationDTO> allByAppIntegrationTypeAndUserId = oauthIntegrationServiceMock.findAllIntegrations(userDTO.getId());
        assertThat(allByAppIntegrationTypeAndUserId.size()).isEqualTo((2));
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
        AppSlackIntegrationDTO appSlackIntegrationDTO = new AppSlackIntegrationDTOBuilder()
                .withAccessToken("accessTokenDummyValue")
                .withScopes("identify,basic")
                .withTeamId("nofameTeamId")
                .withTeamName("yahoo")
                .withUserId("U23UserId")
                .build();
        oauthIntegrationServiceMock.createOauthIntegrationSlack(appSlackIntegrationDTO, createdUserDTO.getId());

        List<AppIntegrationDTO> allByAppIntegrationTypeAndUserId = oauthIntegrationServiceMock.findAllIntegrations(userDTO.getId());
        assertThat(allByAppIntegrationTypeAndUserId.size()).isEqualTo((1));

        oauthIntegrationServiceMock.removeIntegration(allByAppIntegrationTypeAndUserId.get(0).getId(), userDTO.getId());

        assertThat(oauthIntegrationServiceMock.findAllIntegrations(userDTO.getId()).size()).isEqualTo((0));
    }

    private void prepare(AppIntegrationServiceImpl oauthIntegrationServiceMock) throws PaymentException {
        setFieldViaReflection(oauthIntegrationServiceMock.getClass(), oauthIntegrationServiceMock, "userRepository", userRepository);
        setFieldViaReflection(oauthIntegrationServiceMock.getClass(), oauthIntegrationServiceMock, "oauthIntegrationSlackRepository", oauthIntegrationSlackRepository);
        setFieldViaReflection(oauthIntegrationServiceMock.getClass(), oauthIntegrationServiceMock, "dozerBeanMapper", dozerBeanMapper);
    }
}