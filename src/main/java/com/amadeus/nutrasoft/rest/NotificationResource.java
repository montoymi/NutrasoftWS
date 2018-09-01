package com.amadeus.nutrasoft.rest;


import com.amadeus.nutrasoft.model.Notification;
import com.amadeus.nutrasoft.service.NotificationService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.OK;

@Path("/")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class NotificationResource {
    private NotificationService notificationService = new NotificationService();

    @GET
    @Path("notifications/{id}")
    public Response getNotificationsByUserId(@PathParam("id") int userId) {
        List<Notification> notificationList = notificationService.getNotificationsByUserId(userId);
        return Response.status(OK).entity(notificationList).build();
    }
}
