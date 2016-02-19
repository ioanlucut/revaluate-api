package com.revaluate.oauth.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.core.bootstrap.ConfigProperties;
import com.revaluate.oauth.exception.AppIntegrationException;
import com.revaluate.resource.payment.PaymentException;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class AppIntegrationGranterServiceImplTestIT extends AbstractIntegrationTests {

    @Autowired
    private ConfigProperties configProperties;

    //state={"client_id":"2151987168.10687444405","network":"slack","redirect_uri":"http://localhost:3000/","scope":"identify,basic","oauth_proxy":"http://localhost:8080/oauth/grant"}&access_token=123456

    @Test
    public void grantOauthIntegration_getIdentityOf_throwsException() throws Exception {
        AppIntegrationGranterServiceImpl appIntegrationGranterServiceMock = Mockito.spy(new AppIntegrationGranterServiceImpl());
        prepare(appIntegrationGranterServiceMock);

        //-----------------------------------------------------------------
        // Throw OauthIntegrationException
        //-----------------------------------------------------------------
        when(appIntegrationGranterServiceMock.getAccessTokenFrom(anyString(), anyString())).thenThrow(new AppIntegrationException());

        exception.expect(AppIntegrationException.class);
        appIntegrationGranterServiceMock.grantOauthIntegration("code", "state");
    }

    @Test
    public void grantOauthIntegration_getAccessTokenFromIsOk_isOk() throws Exception {
        AppIntegrationGranterServiceImpl appIntegrationGranterServiceMock = Mockito.spy(new AppIntegrationGranterServiceImpl());
        prepare(appIntegrationGranterServiceMock);

        //-----------------------------------------------------------------
        // Throw OauthIntegrationException
        //-----------------------------------------------------------------
        Map<String, String> map = new HashMap<>();
        map.put("access_token", "dsa");
        map.put("scope", "identify");

        String state = "{\"client_id\":\"2151987168.10687444405\",\"network\":\"slack\",\"redirect_uri\":\"http://localhost:3000/\",\"scope\":\"identify,basic\",\"oauth_proxy\":\"http://localhost:8080/oauth/grant\"}";
        when(appIntegrationGranterServiceMock.getAccessTokenFrom(anyString(), anyString())).thenReturn(map);

        URI grantedAsRedirectURI = appIntegrationGranterServiceMock.grantOauthIntegration("authCode", state);

        assertThat(grantedAsRedirectURI).isNotNull();
        assertThat(grantedAsRedirectURI.toString()).isEqualTo(("http://localhost:3000/#state=%7B%22client_id%22%3A%222151987168.10687444405%22%2C%22network%22%3A%22slack%22%2C%22redirect_uri%22%3A%22http%3A%2F%2Flocalhost%3A3000%2F%22%2C%22scope%22%3A%22identify%2Cbasic%22%2C%22oauth_proxy%22%3A%22http%3A%2F%2Flocalhost%3A8080%2Foauth%2Fgrant%22%7D" + "&access_token=" + map.get("access_token")));
    }

    @Test
    public void grantOauthIntegration_invalidFormat_handledOk() throws Exception {
        AppIntegrationGranterServiceImpl appIntegrationGranterServiceMock = Mockito.spy(new AppIntegrationGranterServiceImpl());
        prepare(appIntegrationGranterServiceMock);

        //-----------------------------------------------------------------
        // Throw OauthIntegrationException
        //-----------------------------------------------------------------
        Map<String, String> map = new HashMap<>();
        map.put("access_token", "dsa");
        map.put("scope", "identify");

        when(appIntegrationGranterServiceMock.getAccessTokenFrom(anyString(), anyString())).thenReturn(map);
        String state;

        //-----------------------------------------------------------------
        // Wrong network
        //-----------------------------------------------------------------
        exception.expect(AppIntegrationException.class);
        state = "{\"client_id\":\"2151987168.10687444405\",\"network\":\"WRONG_NETWORK\",\"redirect_uri\":\"http://localhost:3000/\",\"scope\":\"identify,basic\",\"oauth_proxy\":\"http://localhost:8080/oauth/grant\"}";
        appIntegrationGranterServiceMock.grantOauthIntegration("authCode", state);

        //-----------------------------------------------------------------
        // Missing network
        //-----------------------------------------------------------------
        exception.expect(AppIntegrationException.class);
        state = "{\"client_id\":\"2151987168.10687444405\",\"redirect_uri\":\"http://localhost:3000/\",\"scope\":\"identify,basic\",\"oauth_proxy\":\"http://localhost:8080/oauth/grant\"}";
        appIntegrationGranterServiceMock.grantOauthIntegration("authCode", state);

        //-----------------------------------------------------------------
        // Missing redirect_uri
        //-----------------------------------------------------------------
        exception.expect(AppIntegrationException.class);
        state = "{\"client_id\":\"2151987168.10687444405\",\"network\":\"slack\",\"scope\":\"identify,basic\",\"oauth_proxy\":\"http://localhost:8080/oauth/grant\"}";
        appIntegrationGranterServiceMock.grantOauthIntegration("authCode", state);
    }

    private void prepare(AppIntegrationGranterServiceImpl appIntegrationGranterServiceMock) throws PaymentException {
        setFieldViaReflection(appIntegrationGranterServiceMock.getClass(), appIntegrationGranterServiceMock, "configProperties", configProperties);
    }
}
