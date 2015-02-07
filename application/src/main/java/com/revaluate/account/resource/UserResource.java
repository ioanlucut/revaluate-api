package com.revaluate.account.resource;

import com.revaluate.account.domain.UserDomain;
import com.revaluate.account.model.User;
import com.revaluate.account.repository.UserRepository;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
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
    private static final String UPDATE_USER = "update";
    private static final String REMOVE_USER = "remove/{userId}";
    private static final String USER_ID = "userId";

    @Autowired
    private UserRepository userRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(IS_UNIQUE_EMAIL)
    public Response isUnique(@QueryParam(EMAIL) @NotBlank @Email String email) {
        LOGGER.info("Checking if email is unique - {email}", email);

        List<User> byEmail = userRepository.findByEmail(email);

        if (byEmail.isEmpty()) {
            return Response.status(Response.Status.OK).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).entity("Email is not unique").build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(CREATE_USER)
    public Response create(@Valid UserDomain userDomain) {
        if (!userRepository.findByEmail(userDomain.getEmail()).isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Email is not unique").build();
        }

        User user = new User();
        BeanUtils.copyProperties(userDomain, user);

        User savedUser = userRepository.save(user);
        if (savedUser != null) {
            return Response.status(Response.Status.OK).entity(savedUser).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).entity("Something went really bad").build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(UPDATE_USER)
    public Response update(@Valid UserDomain userDomain) {
        User foundUser = userRepository.findOne(userDomain.getId());
        if (foundUser == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("User not found").build();
        }

        // Copy only selective properties
        BeanUtils.copyProperties(userDomain, foundUser, "id", "email", "password");

        User updatedUser = userRepository.save(foundUser);
        if (updatedUser != null) {
            return Response.status(Response.Status.OK).entity(updatedUser).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).entity("Something went really bad").build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path(DETAILS_USER)
    public Response getUserDetails(@PathParam(USER_ID) @NotNull @Min(1) int userId) {
        User foundUser = userRepository.findOne(userId);

        if (foundUser != null) {
            return Response.status(Response.Status.OK).entity(foundUser).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).entity("Something went really bad").build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path(REMOVE_USER)
    public Response remove(@PathParam(USER_ID) @NotNull @Min(1) int userId) {
        userRepository.delete(userId);

        return Response.status(Response.Status.OK).entity("Successfully removed").build();
    }
}