package com.revaluate.account.resource;

import com.revaluate.account.domain.LoginDomain;
import com.revaluate.account.domain.UpdatePasswordDomain;
import com.revaluate.account.domain.UserDomain;
import com.revaluate.account.exception.UserNotFoundException;
import com.revaluate.account.model.User;
import com.revaluate.account.repository.UserRepository;
import com.revaluate.core.Responses;
import com.revaluate.core.filters.Public;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path(UserResource.ACCOUNT)
@Component
public class UserResource extends Resource {

    public static final String ACCOUNT = "account";
    private static final String DETAILS_USER = "details/{userId}";
    private static final String IS_UNIQUE_EMAIL = "isUniqueEmail";
    private static final String EMAIL = "email";
    private static final String CREATE_USER = "create";
    private static final String LOGIN_USER = "login";
    private static final String UPDATE_USER = "update";
    private static final String UPDATE_USER_PASSWORD = "updatePassword";
    private static final String REMOVE_USER = "remove/{userId}";
    private static final String USER_ID = "userId";

    @Autowired
    private UserRepository userRepository;

    @GET
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(IS_UNIQUE_EMAIL)
    public Response isUnique(@QueryParam(EMAIL) @NotBlank @Email String email) {
        LOGGER.info("Checking if email is unique - {email}", email);

        List<User> byEmail = userRepository.findByEmail(email);

        if (byEmail.isEmpty()) {
            return Response.status(Response.Status.OK).build();
        }

        return Responses.respond(Response.Status.BAD_REQUEST, "Email is not unique");
    }

    @POST
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(CREATE_USER)
    public Response create(@Valid UserDomain userDomain) {
        if (!userRepository.findByEmail(userDomain.getEmail()).isEmpty()) {
            return Responses.respond(Response.Status.BAD_REQUEST, "Email is not unique");
        }

        User user = new User();
        BeanUtils.copyProperties(userDomain, user);

        // Hash the password
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

        User savedUser = userRepository.save(user);
        if (savedUser != null) {
            return jwtService.returnJwtResponse(savedUser.getId(), savedUser);
        }

        return Responses.respond(Response.Status.BAD_REQUEST, "Error");
    }

    @POST
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(LOGIN_USER)
    public Response login(@Valid LoginDomain loginDomain) {
        User foundUser = userRepository.findFirstByEmail(loginDomain.getEmail());
        if (foundUser == null) {
            throw new UserNotFoundException("Invalid email or password");
        }

        if (!BCrypt.checkpw(loginDomain.getPassword(), foundUser.getPassword())) {
            throw new UserNotFoundException("Invalid email or password");
        }

        return jwtService.returnJwtResponse(foundUser.getId(), "Successfully logged in");
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(UPDATE_USER)
    public Response update(@Valid UserDomain userDomain) {
        User foundUser = userRepository.findOne(userDomain.getId());
        if (foundUser == null) {
            throw new UserNotFoundException();
        }

        // Copy only selective properties
        BeanUtils.copyProperties(userDomain, foundUser, "id", "email", "password");

        User updatedUser = userRepository.save(foundUser);
        if (updatedUser != null) {
            return Responses.respond(Response.Status.OK, updatedUser);
        }

        return Responses.respond(Response.Status.BAD_REQUEST, "Error");
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path(DETAILS_USER)
    public Response getUserDetails(@PathParam(USER_ID) @NotNull @Min(1) int userId) {
        User foundUser = userRepository.findOne(userId);

        if (foundUser != null) {
            return Responses.respond(Response.Status.OK, foundUser);
        }

        return Responses.respond(Response.Status.BAD_REQUEST, "Error");
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path(REMOVE_USER)
    public Response remove(@PathParam(USER_ID) @NotNull @Min(1) int userId) {
        userRepository.delete(userId);

        return Responses.respond(Response.Status.OK, "Successfully removed");
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(UPDATE_USER_PASSWORD)
    public Response updatePassword(@Valid UpdatePasswordDomain updatePasswordDomain) {
        if (!updatePasswordDomain.getNewPassword().equals(updatePasswordDomain.getNewPasswordConfirmation())) {
            return Responses.respond(Response.Status.BAD_REQUEST, "New password should match new password confirmation");
        }

        User existingUser = userRepository.findOne(getCurrentUserId());
        if (existingUser == null) {
            throw new UserNotFoundException("Invalid email or password");
        }

        if (!BCrypt.checkpw(updatePasswordDomain.getOldPassword(), existingUser.getPassword())) {
            throw new UserNotFoundException("Current password is wrong");
        }

        existingUser.setPassword(BCrypt.hashpw(updatePasswordDomain.getNewPassword(), BCrypt.gensalt()));

        User updatedUser = userRepository.save(existingUser);
        if (updatedUser != null) {
            return Responses.respond(Response.Status.OK, updatedUser);
        }

        return Responses.respond(Response.Status.BAD_REQUEST, "Error while trying to update the password.");
    }
}