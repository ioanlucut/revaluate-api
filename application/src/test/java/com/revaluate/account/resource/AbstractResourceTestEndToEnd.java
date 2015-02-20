package com.revaluate.account.resource;

import com.revaluate.core.bootstrap.ApplicationConfig;
import com.revaluate.core.bootstrap.ConfigProperties;
import com.revaluate.core.jwt.JwtService;
import org.glassfish.jersey.server.validation.ValidationError;
import org.glassfish.jersey.test.JerseyTest;
import org.mockito.Mockito;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AbstractResourceTestEndToEnd extends JerseyTest {

    protected static final String JWT_ISSUER_TEST = "http://revaluate.io";
    protected static final String JWT_SHARED_TEST = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    protected static final String BEARER_TEST = "Bearer";
    protected JwtService jwtService = getMockedJwtService();

    protected List<ValidationError> getValidationErrorList(final Response response) {
        return response.readEntity(new GenericType<List<ValidationError>>() {
        });
    }

    protected Set<String> getValidationMessageTemplates(final Response response) {
        return getValidationMessageTemplates(getValidationErrorList(response));
    }

    protected Set<String> getValidationMessageTemplates(final List<ValidationError> errors) {
        return errors.stream().map(ValidationError::getMessageTemplate).collect(Collectors.toSet());
    }

    @Override
    protected Application configure() {
        return new ApplicationConfig();
    }

    protected JwtService getMockedJwtService() {
        ConfigProperties configProperties = Mockito.mock(ConfigProperties.class);
        Mockito.when(configProperties.getIssuer()).thenReturn(JWT_ISSUER_TEST);
        Mockito.when(configProperties.getShared()).thenReturn(JWT_SHARED_TEST);
        Mockito.when(configProperties.getBearerHeaderKey()).thenReturn(BEARER_TEST);
        JwtService jwtService = Mockito.spy(new JwtService());
        jwtService.setConfigProperties(configProperties);
        return jwtService;
    }
}