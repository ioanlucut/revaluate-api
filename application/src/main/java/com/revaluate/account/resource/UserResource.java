package com.revaluate.account.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.revaluate.account.domain.LoginDTO;
import com.revaluate.account.domain.ResetPasswordDTO;
import com.revaluate.account.domain.UpdatePasswordDTO;
import com.revaluate.account.domain.UserDTO;
import com.revaluate.core.views.Views;
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
    private static final String TOKEN = "token";
    private static final String CREATE_USER = "create";
    private static final String LOGIN_USER = "login";
    private static final String UPDATE_USER = "update";
    private static final String UPDATE_USER_PASSWORD = "updatePassword";
    private static final String REQUEST_RESET_PASSWORD = "requestResetPassword/{email}";
    private static final String VALIDATE_RESET_PASSWORD_TOKEN = "validateResetPasswordToken/{email}/{token}";
    private static final String RESET_PASSWORD = "resetPassword/{email}/{token}";
    private static final String REMOVE_USER = "remove";

    @Autowired
    private UserService userService;

    @GET
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(IS_UNIQUE_EMAIL)
    public Response isUnique(@QueryParam(EMAIL) @NotBlank @Email String email) throws UserException {
        if (!userService.isUnique(email)) {
            throw new UserException("Email is not unique");
        }

        return Responses.respond(Response.Status.OK, "Email is unique.");
    }

    @POST
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(CREATE_USER)
    @JsonView({Views.StrictView.class})
    public Response create(@Valid UserDTO userDTO) throws UserException {
        UserDTO createdUserDTO = userService.create(userDTO);

        return Responses.respond(Response.Status.OK, createdUserDTO);
    }

    @POST
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(LOGIN_USER)
    @JsonView({Views.StrictView.class})
    public Response login(@Valid LoginDTO loginDTO) throws UserException {
        UserDTO foundUserDTO = userService.login(loginDTO);
        String jwtToken = jwtService.tryToGetUserToken(foundUserDTO.getId());

        return Response.status(Response.Status.OK).entity(foundUserDTO).header(configProperties.getAuthTokenHeaderKey(), jwtToken).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(UPDATE_USER)
    @JsonView({Views.StrictView.class})
    public Response update(@Valid UserDTO userDTO) throws UserException {
        UserDTO updatedUserDTO = userService.update(userDTO, getCurrentUserId());

        return Responses.respond(Response.Status.OK, updatedUserDTO);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path(DETAILS_USER)
    @JsonView({Views.DetailsView.class})
    public Response getUserDetails() throws UserException {
        UserDTO userDetailsDTO = userService.getUserDetails(getCurrentUserId());

        return Responses.respond(Response.Status.OK, userDetailsDTO);
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
    public Response updatePassword(@Valid UpdatePasswordDTO updatePasswordDTO) throws UserException {
        userService.updatePassword(updatePasswordDTO, getCurrentUserId());

        return Responses.respond(Response.Status.OK, "Password successfully updated");
    }

    @POST
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(REQUEST_RESET_PASSWORD)
    public Response requestResetPassword(@PathParam(EMAIL) @NotBlank @Email String email) throws UserException {
        userService.requestResetPassword(email);

        return Responses.respond(Response.Status.OK, "Token sent per email successful.");
    }

    @POST
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(VALIDATE_RESET_PASSWORD_TOKEN)
    public Response validateResetPasswordToken(@PathParam(EMAIL) @NotBlank @Email String email, @PathParam(TOKEN) @NotBlank String token) throws UserException {
        userService.validateResetPasswordToken(email, token);

        return Responses.respond(Response.Status.OK, "Token is valid");
    }

    @POST
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(RESET_PASSWORD)
    public Response resetPassword(@Valid ResetPasswordDTO resetPasswordDTO, @PathParam(EMAIL) @NotBlank @Email String email, @PathParam(TOKEN) @NotBlank String token) throws UserException {
        userService.resetPassword(resetPasswordDTO, email, token);

        return Responses.respond(Response.Status.OK, "Password successfully reset.");
    }
}