package com.amadeus.nutrasoft.rest;


import com.amadeus.nutrasoft.model.User;
import com.amadeus.nutrasoft.service.UserService;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;

@Path("/users")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class UserResource {
    private UserService userService = new UserService();

    @POST
    @Path("/photos")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPhoto(@FormDataParam("file") InputStream inputStream, @FormDataParam("file") FormDataContentDisposition fileMetaData) {
        String name = fileMetaData.getFileName();
        userService.uploadPhoto(inputStream, name);
        return Response.ok(name).build();
    }

    @GET
    @Path("/photos/{id}")
    @Produces("image/jpeg")
    public Response downloadPhoto(@PathParam("id") int id) {
        InputStream inputStream = userService.downloadPhoto(id);
        return Response.ok(inputStream).header("Content-Disposition", "attachment").build();
    }

    @POST
    public Response createUser(User user) {
        userService.createUser(user);
        return Response.status(CREATED).entity(user).build();
    }

    @PUT
    public Response updateUser(User user) {
        userService.updateUser(user);
        return Response.status(OK).entity(user).build();
    }

    @GET
    @Path("/{email}")
    public Response getUserByEmail(@PathParam("email") String email) {
        User coach = userService.getUserByEmail(email);
        return Response.status(OK).entity(coach).build();
    }

    @GET
    public Response getUsersByUserType(@QueryParam("user-type") String userType) {
        List<User> userList = userService.getUsersByUserType(userType);
        return Response.status(OK).entity(userList).build();
    }
}
