package com.amadeus.nutrasoft.rest;


import com.amadeus.nutrasoft.model.User;
import com.amadeus.nutrasoft.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;

@Path("/")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class UserResource {
    private UserService userService = new UserService();

    @POST
    @Path("users")
    public Response createUser(User user) {
        userService.createUser(user);
        return Response.status(CREATED).entity(user).build();
    }

    @PUT
    @Path("users/{id}")
    public Response updateUser(User user) {
        userService.updateUser(user);
        return Response.status(OK).entity(user).build();
    }

    @GET
    @Path("users/{id}")
    public Response getUserByEmail(@PathParam("id") String email) {
        User user = userService.getUserByEmail(email);
        return Response.status(OK).entity(user).build();
    }

    @GET
    @Path("coaches")
    public Response getAllCoaches() {
        List<User> userList = userService.getAllCoaches();
        return Response.status(OK).entity(userList).build();
    }

    @GET
    @Path("coaches/{id}/clients")
    public Response getClientsByCoachId(@PathParam("id") int coachId) {
        List<User> userList = userService.getClientsByCoachId(coachId);
        return Response.status(OK).entity(userList).build();
    }

    @GET
    @Path("users/{id}/photos")
    @Produces("image/jpeg")
    public Response getPhotoByUserId(@PathParam("id") int id) {
        InputStream inputStream = userService.getPhotoByUserId(id);
        return Response.ok(inputStream).header("Content-Disposition", "attachment").build();
    }
}
