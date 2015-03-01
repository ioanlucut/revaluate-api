package com.revaluate.core.filters;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.domain.UserDTO;
import com.revaluate.account.domain.UserDTOBuilder;
import com.revaluate.account.persistence.User;
import com.revaluate.core.jwt.JwtService;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.container.ContainerRequestContext;

public class AuthorizationRequestFilterTestIT extends AbstractIntegrationTests {

    @Autowired
    private JwtService jwtService;

    @Test
    public void publicMethod() throws Exception {
        AuthorizationRequestFilter authorizationRequestFilter = Mockito.spy(new AuthorizationRequestFilter());
        Mockito.doReturn(true).when(authorizationRequestFilter).isPublicMethod();

        ContainerRequestContext containerRequestContext = Mockito.mock(ContainerRequestContext.class);
        Mockito.when(containerRequestContext.getHeaderString("Authorization")).thenReturn(null);

        authorizationRequestFilter.filter(containerRequestContext);

        Mockito.verify(authorizationRequestFilter, Mockito.never()).abort(Mockito.any());
    }

    @Test
    public void nonPublicMethodWithoutToken() throws Exception {
        AuthorizationRequestFilter authorizationRequestFilter = Mockito.spy(new AuthorizationRequestFilter());
        Mockito.doReturn(false).when(authorizationRequestFilter).isPublicMethod();

        ContainerRequestContext containerRequestContext = Mockito.mock(ContainerRequestContext.class);
        Mockito.when(containerRequestContext.getHeaderString("Authorization")).thenReturn(null);

        authorizationRequestFilter.filter(containerRequestContext);

        Mockito.verify(authorizationRequestFilter, Mockito.times(1)).abort(Mockito.any());
    }

    @Test
    public void nonPublicMethodWithEmptyToken() throws Exception {
        AuthorizationRequestFilter authorizationRequestFilter = Mockito.spy(new AuthorizationRequestFilter());
        Mockito.doReturn(false).when(authorizationRequestFilter).isPublicMethod();

        ContainerRequestContext containerRequestContext = Mockito.mock(ContainerRequestContext.class);
        Mockito.when(containerRequestContext.getHeaderString("Authorization")).thenReturn("");

        authorizationRequestFilter.filter(containerRequestContext);

        Mockito.verify(authorizationRequestFilter, Mockito.times(1)).abort(Mockito.any());
    }

    @Test
    public void nonPublicMethodWithIncompleteAndInvalidToken() throws Exception {
        AuthorizationRequestFilter authorizationRequestFilter = Mockito.spy(new AuthorizationRequestFilter());
        authorizationRequestFilter.setJwtService(jwtService);
        authorizationRequestFilter.setUserRepository(userRepository);
        Mockito.doReturn(false).when(authorizationRequestFilter).isPublicMethod();

        ContainerRequestContext containerRequestContext = Mockito.mock(ContainerRequestContext.class);
        Mockito.when(containerRequestContext.getHeaderString("Authorization")).thenReturn("Bearer");

        authorizationRequestFilter.filter(containerRequestContext);

        Mockito.verify(authorizationRequestFilter, Mockito.times(1)).abort(Mockito.any());
    }

    @Test
    public void nonPublicMethodWithInvalidToken() throws Exception {
        AuthorizationRequestFilter authorizationRequestFilter = Mockito.spy(new AuthorizationRequestFilter());
        authorizationRequestFilter.setJwtService(jwtService);
        authorizationRequestFilter.setUserRepository(userRepository);
        Mockito.doReturn(false).when(authorizationRequestFilter).isPublicMethod();

        ContainerRequestContext containerRequestContext = Mockito.mock(ContainerRequestContext.class);
        Mockito.when(containerRequestContext.getHeaderString("Authorization")).thenReturn("Bearer xxx");

        authorizationRequestFilter.filter(containerRequestContext);

        Mockito.verify(authorizationRequestFilter, Mockito.times(1)).abort(Mockito.any());
    }

    @Test
    public void nonPublicMethodWithValidToken() throws Exception {
        AuthorizationRequestFilter authorizationRequestFilter = Mockito.spy(new AuthorizationRequestFilter());
        authorizationRequestFilter.setJwtService(jwtService);
        authorizationRequestFilter.setUserRepository(userRepository);
        Mockito.doReturn(false).when(authorizationRequestFilter).isPublicMethod();

        User newUserEntity = new User();
        UserDTO newUser = new UserDTOBuilder().withEmail("xx@xx.xx").withFirstName("fn").withLastName("ln").withPassword("1234567").build();
        BeanUtils.copyProperties(newUser, newUserEntity);
        User savedUser = userRepository.save(newUserEntity);
        String generatedToken = jwtService.createTokenForUserWithId(savedUser.getId());

        ContainerRequestContext containerRequestContext = Mockito.mock(ContainerRequestContext.class);
        Mockito.when(containerRequestContext.getHeaderString("Authorization")).thenReturn("Bearer " + generatedToken);

        authorizationRequestFilter.filter(containerRequestContext);

        Mockito.verify(authorizationRequestFilter, Mockito.never()).abort(Mockito.any());

        userRepository.delete(savedUser.getId());
    }

    @Test
    public void nonPublicMethodWithValidTokenButInvalidUser() throws Exception {
        AuthorizationRequestFilter authorizationRequestFilter = Mockito.spy(new AuthorizationRequestFilter());
        authorizationRequestFilter.setJwtService(jwtService);
        authorizationRequestFilter.setUserRepository(userRepository);
        Mockito.doReturn(false).when(authorizationRequestFilter).isPublicMethod();

        String generatedToken = jwtService.createTokenForUserWithId(99999999);

        ContainerRequestContext containerRequestContext = Mockito.mock(ContainerRequestContext.class);
        Mockito.when(containerRequestContext.getHeaderString("Authorization")).thenReturn("Bearer " + generatedToken);

        authorizationRequestFilter.filter(containerRequestContext);

        Mockito.verify(authorizationRequestFilter, Mockito.times(1)).abort(Mockito.any());
    }
}