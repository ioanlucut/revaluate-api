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
    private static final String REQUEST_RESET_PASSWORD = "resetPassword";
    private static final String REMOVE_USER = "remove";

    @Autowired
    private UserService userService;

    @GET
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(IS_UNIQUE_EMAIL)
    public Response isUnique(@QueryParam(EMAIL) @NotBlank @Email String email) {
        if (userService.isUnique(email)) {
            return Responses.respond(Response.Status.OK, "");
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

            return Responses.respond(Response.Status.OK, createdUserDTO);
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
            String jwtToken = jwtService.tryToGetUserToken(foundUserDTO.getId());

            return Response.status(Response.Status.OK).entity(foundUserDTO).header(configProperties.getAuthTokenHeaderKey(), jwtToken).build();
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

            return Responses.respond(Response.Status.OK, updatedUserDTO);
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

            return Responses.respond(Response.Status.OK, userDetailsDTO);
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

            return Responses.respond(Response.Status.OK, updatedUserDTO);
        } catch (UserException ex) {
            return Responses.respond(Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    @POST
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(REQUEST_RESET_PASSWORD)
    public Response requestResetPassword(@QueryParam(EMAIL) @NotBlank @Email String email) {
        try {
            userService.requestResetPassword(email);

            return Responses.respond(Response.Status.OK, "Password successfully reset.");
        } catch (UserException ex) {
            return Responses.respond(Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }
}