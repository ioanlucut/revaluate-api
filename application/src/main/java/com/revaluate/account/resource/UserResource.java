package com.revaluate.account.resource;

import com.revaluate.account.domain.LoginDTO;
import com.revaluate.account.domain.UpdatePasswordDTO;
import com.revaluate.account.domain.UserDTO;
import com.revaluate.account.exception.UserException;
import com.revaluate.account.service.UserService;
import com.revaluate.core.annotations.Public;
import com.revaluate.core.resource.Resource;
import com.revaluate.core.resource.Responses;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(UserResource.ACCOUNT)
@Component
public class UserResource extends Resource {

    public static final String ACCOUNT = "account";
    private static final String DETAILS_USER = "details";
    private static final String IS_UNIQUE_EMAIL = "isUniqueEmail";
    private static final String EMAIL = "email";
    private static final String CREATE_USER = "create";
    private static final String LOGIN_USER = "login";
    private static final String UPDATE_USER = "update";
    private static final String UPDATE_USER_PASSWORD = "updatePassword";
    private static final String REQUEST_SIGNUP_REGISTRATION = "requestSignUpRegistration";
    private static final String REMOVE_USER = "remove";
    private static final String USER_ID = "userId";

    @Autowired
    private UserService userService;

    @GET
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(IS_UNIQUE_EMAIL)
    public Response isUnique(@QueryParam(EMAIL) @NotBlank @Email String email) {
        if (userService.isUnique(email)) {
            return Response.status(Response.Status.OK).build();
        }

        return Responses.respond(Response.Status.BAD_REQUEST, "Email is not unique");
    }

    @POST
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(CREATE_USER)
    public Response create(@Valid UserDTO userDTO) {
        try {
            UserDTO createdUserDTO = userService.create(userDTO);
            String jwtToken = jwtService.tryToGetUserToken(createdUserDTO.getId());

            return Response.status(Response.Status.OK).entity(createdUserDTO).header(configProperties.getAuthTokenHeaderKey(), jwtToken).build();

        } catch (UserException ex) {
            return Responses.respond(Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    @POST
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(LOGIN_USER)
    public Response login(@Valid LoginDTO loginDTO) {
        try {
            UserDTO foundUserDTO = userService.login(loginDTO);

            return Response.status(Response.Status.OK).entity(foundUserDTO).build();
        } catch (UserException ex) {
            return Responses.respond(Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(UPDATE_USER)
    public Response update(@Valid UserDTO userDTO) {
        try {
            UserDTO updatedUserDTO = userService.update(userDTO, getCurrentUserId());

            return Response.status(Response.Status.OK).entity(updatedUserDTO).build();
        } catch (UserException ex) {
            return Responses.respond(Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path(DETAILS_USER)
    public Response getUserDetails() {
        try {
            UserDTO userDetailsDTO = userService.getUserDetails(getCurrentUserId());

            return Response.status(Response.Status.OK).entity(userDetailsDTO).build();
        } catch (UserException ex) {
            return Responses.respond(Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path(REMOVE_USER)
    public Response remove() {
        userService.remove(getCurrentUserId());

        return Responses.respond(Response.Status.OK, "User successfully removed");
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(UPDATE_USER_PASSWORD)
    public Response updatePassword(@Valid UpdatePasswordDTO updatePasswordDTO) {
        try {
            UserDTO updatedUserDTO = userService.updatePassword(updatePasswordDTO, getCurrentUserId());

            return Response.status(Response.Status.OK).entity(updatedUserDTO).build();
        } catch (UserException ex) {
            return Responses.respond(Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    @POST
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(REQUEST_SIGNUP_REGISTRATION)
    public Response requestSignUpRegistration(@QueryParam(EMAIL) @NotBlank @Email String email) {
        try {
            UserDTO updatedUserDTO = userService.requestSignUpRegistration(email);

            return Response.status(Response.Status.OK).entity(updatedUserDTO).build();
        } catch (UserException ex) {
            return Responses.respond(Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }
}