package com.revaluate.account.resource;

import com.revaluate.account.dto.UserDto;
import com.revaluate.account.model.User;
import com.revaluate.account.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("account")
@Component
public class UserResource extends Resource {

    private static final String DETAILS_USER = "details";
    private static final String IS_UNIQUE_EMAIL = "isUniqueEmail";
    private static final String EMAIL = "email";
    private static final String CREATE_USER = "create";
    private static final String UPDATE_USER = "update";

    @Autowired
    private UserRepository userRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path(DETAILS_USER)
    public User getUser() {

        return userRepository.findOne(1L);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path(IS_UNIQUE_EMAIL)
    public Response isUnique(@QueryParam(EMAIL) String email) {
        LOGGER.info("cu emailul {email}", email);
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
    public Response create(UserDto userDto) {
        if (!userRepository.findByEmail(userDto.getEmail()).isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Email is not unique").build();
        }

        User user = new User();
        BeanUtils.copyProperties(userDto, user);

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
    public Response update(UserDto userDto) {
        User foundUser = userRepository.findOne(userDto.getId());
        if (foundUser == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("User not found").build();
        }

        if (!foundUser.getFirstName().equals(userDto.getFirstName())) {
            foundUser.setFirstName(userDto.getFirstName());
        }
        if (!foundUser.getLastName().equals(userDto.getLastName())) {
            foundUser.setLastName(userDto.getLastName());
        }

        // Copy only selective properties
        BeanUtils.copyProperties(userDto, foundUser, "id", "email", "password");

        User updatedUser = userRepository.save(foundUser);
        if (updatedUser != null) {
            return Response.status(Response.Status.OK).entity(updatedUser).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).entity("Something went really bad").build();
    }
}