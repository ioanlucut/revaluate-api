package com.revaluate.resource.account;

import com.fasterxml.jackson.annotation.JsonView;
import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.UserPartialUpdateEnum;
import com.revaluate.account.service.UserService;
import com.revaluate.core.annotations.Public;
import com.revaluate.core.jwt.JwtException;
import com.revaluate.domain.account.*;
import com.revaluate.groups.CreateUserGroup;
import com.revaluate.groups.UpdateUserAccountDetailsGroup;
import com.revaluate.groups.UpdateUserCurrencyGroup;
import com.revaluate.groups.UpdateUserInitiatedStatusGroup;
import com.revaluate.resource.utils.Resource;
import com.revaluate.resource.utils.Responses;
import com.revaluate.views.Views;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(UserResource.ACCOUNT)
@Component
public class UserResource extends Resource {

    public static final String ACCOUNT = "account";
    private static final String IS_UNIQUE_EMAIL = "isUniqueEmail";
    private static final String EMAIL = "email";
    private static final String TOKEN = "token";
    private static final String LOGIN_USER = "login";
    private static final String UPDATE_CURRENCY = "updateCurrency";
    private static final String UPDATE_INITIATED_STATUS = "updateInitiatedStatus";
    private static final String UPDATE_ACCOUNT_DETAILS = "updateAccountDetails";
    private static final String UPDATE_USER_PASSWORD = "updatePassword";
    private static final String REQUEST_RESET_PASSWORD = "requestResetPassword/{email}";
    private static final String REQUEST_CONFIRMATION_EMAIL = "requestConfirmationEmail/{email}";
    private static final String VALIDATE_RESET_PASSWORD_TOKEN = "validateResetPasswordToken/{email}/{token}";
    private static final String VALIDATE_CONFIRMATION_EMAIL_TOKEN = "validateConfirmationEmailToken/{email}/{token}";
    private static final String RESET_PASSWORD = "resetPassword/{email}/{token}";
    private static final String SEND_FEEDBACK = "sendFeedback";

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
    @JsonView({Views.StrictView.class})
    public Response create(@Validated(CreateUserGroup.class) UserDTO userDTO) throws UserException {
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
        try {
            String jwtToken = jwtService.tryToGetUserToken(foundUserDTO.getId());

            return Response.status(Response.Status.OK).entity(foundUserDTO).header(configProperties.getAuthTokenHeaderKey(), jwtToken).build();
        } catch (JwtException ex) {
            throw new UserException(ex.getMessage(), ex);
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @JsonView({Views.StrictView.class})
    @Path(UPDATE_CURRENCY)
    public Response updateCurrency(@Validated(UpdateUserCurrencyGroup.class) UserDTO userDTO) throws UserException {
        UserDTO updatedUserDTO = userService.updateCurrency(userDTO, getCurrentUserId());

        return Responses.respond(Response.Status.OK, updatedUserDTO);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @JsonView({Views.StrictView.class})
    @Path(UPDATE_INITIATED_STATUS)
    public Response updateInitiatedStatus(@Validated(UpdateUserInitiatedStatusGroup.class) UserDTO userDTO) throws UserException {
        UserDTO updatedUserDTO = userService.update(userDTO, getCurrentUserId(), UserPartialUpdateEnum.INITIATED_STATUS);

        return Responses.respond(Response.Status.OK, updatedUserDTO);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @JsonView({Views.StrictView.class})
    @Path(UPDATE_ACCOUNT_DETAILS)
    public Response updateAccountDetails(@Validated(UpdateUserAccountDetailsGroup.class) UserDTO userDTO) throws UserException {
        UserDTO updatedUserDTO = userService.update(userDTO, getCurrentUserId(), UserPartialUpdateEnum.ACCOUNT_DETAILS);

        return Responses.respond(Response.Status.OK, updatedUserDTO);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView({Views.DetailsView.class})
    public Response getUserDetails() throws UserException {
        UserDTO userDetailsDTO = userService.getUserDetails(getCurrentUserId());

        return Responses.respond(Response.Status.OK, userDetailsDTO);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove() {
        try {
            userService.remove(getCurrentUserId());
        } catch (Exception ex) {

            return Responses.respond(Response.Status.BAD_REQUEST, ex.getMessage());
        }

        return Responses.respond(Response.Status.OK, "User successfully removed");
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(REQUEST_CONFIRMATION_EMAIL)
    public Response requestConfirmationEmail(@PathParam(EMAIL) @NotBlank @Email String email) throws UserException {
        userService.requestConfirmationEmail(email);

        return Responses.respond(Response.Status.OK, "Email confirmation sent per email successful.");
    }

    @POST
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(VALIDATE_CONFIRMATION_EMAIL_TOKEN)
    public Response validateConfirmationEmailToken(@PathParam(EMAIL) @NotBlank @Email String email, @PathParam(TOKEN) @NotBlank String token) throws UserException {
        userService.validateConfirmationEmailToken(email, token);

        return Responses.respond(Response.Status.OK, "Confirmation successful");
    }

    @PUT
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

        return Responses.respond(Response.Status.OK, "Confirmation successful");
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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(SEND_FEEDBACK)
    public Response sendFeedback(@Valid FeedbackDTO feedbackDTO) throws UserException {
        userService.sendFeedback(feedbackDTO, getCurrentUserId());

        return Responses.respond(Response.Status.OK, "Feedback successfully sent!");
    }
}