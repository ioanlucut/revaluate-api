package com.revaluate.core.filters;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.core.jwt.JwtService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.container.ContainerRequestContext;

import static org.mockito.Mockito.*;

public class AuthorizationRequestFilterTestIT extends AbstractIntegrationTests {

    @Autowired
    private JwtService jwtService;

    @Test
    public void publicMethod() throws Exception {
        AuthorizationRequestFilter authorizationRequestFilter = spy(new AuthorizationRequestFilter());
        doReturn(true).when(authorizationRequestFilter).isPublicMethod();

        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getHeaderString("Authorization")).thenReturn(null);
        when(containerRequestContext.getMethod()).thenReturn("POST");

        authorizationRequestFilter.filter(containerRequestContext);

        verify(authorizationRequestFilter, never()).abort(any());
    }

    @Test
    public void nonPublicMethodWithoutToken() throws Exception {
        AuthorizationRequestFilter authorizationRequestFilter = spy(new AuthorizationRequestFilter());
        doReturn(false).when(authorizationRequestFilter).isPublicMethod();

        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getHeaderString("Authorization")).thenReturn(null);
        when(containerRequestContext.getMethod()).thenReturn("POST");

        authorizationRequestFilter.filter(containerRequestContext);

        verify(authorizationRequestFilter, times(1)).abort(any());
    }

    @Test
    public void nonPublicMethodWithEmptyToken() throws Exception {
        AuthorizationRequestFilter authorizationRequestFilter = spy(new AuthorizationRequestFilter());
        doReturn(false).when(authorizationRequestFilter).isPublicMethod();

        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getHeaderString("Authorization")).thenReturn("");
        when(containerRequestContext.getMethod()).thenReturn("POST");

        authorizationRequestFilter.filter(containerRequestContext);

        verify(authorizationRequestFilter, times(1)).abort(any());
    }

    @Test
    public void nonPublicMethodWithIncompleteAndInvalidToken() throws Exception {
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
    public void nonPublicMethodWithInvalidToken() throws Exception {
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
    public void nonPublicMethodWithValidToken() throws Exception {
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
    public void nonPublicMethodWithValidTokenButInvalidUser() throws Exception {
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
    public void optionsWorksForNonPublicMethodWithInvalidToken() throws Exception {
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