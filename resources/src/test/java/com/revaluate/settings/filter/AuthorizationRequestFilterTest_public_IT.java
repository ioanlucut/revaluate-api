package com.revaluate.settings.filter;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.core.jwt.JwtService;
import com.revaluate.domain.account.UserDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.container.ContainerRequestContext;

import static org.mockito.Mockito.*;

public class AuthorizationRequestFilterTest_public_IT extends AbstractIntegrationTests {

    @Autowired
    private JwtService jwtService;

    @Test
    public void filter_publicMethod_isOk() throws Exception {
        AuthorizationRequestFilter authorizationRequestFilter = spy(new AuthorizationRequestFilter());
        doReturn(true).when(authorizationRequestFilter).isPublicMethod();

        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getHeaderString("Authorization")).thenReturn(null);
        when(containerRequestContext.getMethod()).thenReturn("POST");

        authorizationRequestFilter.filter(containerRequestContext);

        verify(authorizationRequestFilter, never()).abort(any());
    }

    @Test
    public void filter_nonPublicMethodWithoutToken_isOk() throws Exception {
        AuthorizationRequestFilter authorizationRequestFilter = spy(new AuthorizationRequestFilter());
        doReturn(false).when(authorizationRequestFilter).isPublicMethod();

        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getHeaderString("Authorization")).thenReturn(null);
        when(containerRequestContext.getMethod()).thenReturn("POST");

        authorizationRequestFilter.filter(containerRequestContext);

        verify(authorizationRequestFilter, times(1)).abort(any());
    }

    @Test
    public void filter_nonPublicMethodWithEmptyToken_isOk() throws Exception {
        AuthorizationRequestFilter authorizationRequestFilter = spy(new AuthorizationRequestFilter());
        doReturn(false).when(authorizationRequestFilter).isPublicMethod();

        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getHeaderString("Authorization")).thenReturn("");
        when(containerRequestContext.getMethod()).thenReturn("POST");

        authorizationRequestFilter.filter(containerRequestContext);

        verify(authorizationRequestFilter, times(1)).abort(any());
    }

    @Test
    public void filter_nonPublicMethodWithIncompleteAndInvalidToken_isOk() throws Exception {
        AuthorizationRequestFilter authorizationRequestFilter = spy(new AuthorizationRequestFilter());
        authorizationRequestFilter.setJwtService(jwtService);
        doReturn(false).when(authorizationRequestFilter).isPublicMethod();

        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getHeaderString("Authorization")).thenReturn("Bearer");
        when(containerRequestContext.getMethod()).thenReturn("POST");

        authorizationRequestFilter.filter(containerRequestContext);

        verify(authorizationRequestFilter, times(1)).abort(any());
    }

    @Test
    public void filter_nonPublicMethodWithInvalidToken_isOk() throws Exception {
        AuthorizationRequestFilter authorizationRequestFilter = spy(new AuthorizationRequestFilter());
        authorizationRequestFilter.setJwtService(jwtService);
        doReturn(false).when(authorizationRequestFilter).isPublicMethod();

        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getHeaderString("Authorization")).thenReturn("Bearer xxx");
        when(containerRequestContext.getMethod()).thenReturn("Bearer xxx");
        when(containerRequestContext.getMethod()).thenReturn("POST");

        authorizationRequestFilter.filter(containerRequestContext);

        verify(authorizationRequestFilter, times(1)).abort(any());
    }

    @Test
    public void filter_nonPublicMethodWithValidToken_isOk() throws Exception {
        AuthorizationRequestFilter authorizationRequestFilter = spy(new AuthorizationRequestFilter());
        authorizationRequestFilter.setJwtService(jwtService);
        doReturn(false).when(authorizationRequestFilter).isPublicMethod();

        UserDTO newUser = createUserDTO();
        String generatedToken = jwtService.createTokenForUserWithId(newUser.getId());

        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getHeaderString("Authorization")).thenReturn("Bearer " + generatedToken);
        when(containerRequestContext.getMethod()).thenReturn("POST");

        authorizationRequestFilter.filter(containerRequestContext);

        verify(authorizationRequestFilter, never()).abort(any());
    }

    @Test
    public void filter_nonPublicMethodWithValidTokenButInvalidUser_isOk() throws Exception {
        AuthorizationRequestFilter authorizationRequestFilter = spy(new AuthorizationRequestFilter());
        authorizationRequestFilter.setJwtService(jwtService);
        doReturn(false).when(authorizationRequestFilter).isPublicMethod();

        String generatedToken = jwtService.createTokenForUserWithId(99999999);

        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getHeaderString("Authorization")).thenReturn("Bearer " + generatedToken);
        when(containerRequestContext.getMethod()).thenReturn("POST");

        authorizationRequestFilter.filter(containerRequestContext);

        verify(authorizationRequestFilter, never()).abort(any());
    }

    @Test
    public void filter_nonPublicMethodWithNoBearerInHeader_isOk() throws Exception {
        AuthorizationRequestFilter authorizationRequestFilter = spy(new AuthorizationRequestFilter());
        authorizationRequestFilter.setJwtService(jwtService);
        doReturn(false).when(authorizationRequestFilter).isPublicMethod();

        String generatedToken = jwtService.createTokenForUserWithId(99999999);

        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getHeaderString("Authorization")).thenReturn("" + generatedToken);
        when(containerRequestContext.getMethod()).thenReturn("POST");

        authorizationRequestFilter.filter(containerRequestContext);

        verify(authorizationRequestFilter, times(1)).abort(any());
    }

    @Test
    public void filter_optionsWorksForNonPublicMethodWithInvalidToken_isOk() throws Exception {
        AuthorizationRequestFilter authorizationRequestFilter = spy(new AuthorizationRequestFilter());
        authorizationRequestFilter.setJwtService(jwtService);
        doReturn(false).when(authorizationRequestFilter).isPublicMethod();

        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getHeaderString("Authorization")).thenReturn("Bearer xxx");
        when(containerRequestContext.getMethod()).thenReturn("OPTIONS");

        authorizationRequestFilter.filter(containerRequestContext);

        verify(authorizationRequestFilter, never()).abort(any());
    }
}
