package com.amadeus.nutrasoft.rest;


import com.amadeus.nutrasoft.model.Device;
import com.amadeus.nutrasoft.service.DeviceService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;

@Path("/")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class DeviceResource {
    private DeviceService deviceService = new DeviceService();

    @POST
    @Path("devices")
    public Response createDevice(Device device) {
        deviceService.createDevice(device);
        return Response.status(CREATED).entity(device).build();
    }

    @PUT
    @Path("devices/{id}")
    public Response updateDevice(Device device) {
        deviceService.updateDevice(device);
        return Response.status(OK).entity(device).build();
    }
}
