package com.amadeus.nutrasoft.rest;

import com.amadeus.nutrasoft.model.Measurement;
import com.amadeus.nutrasoft.service.MeasurementService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.OK;

@Path("/measurements")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class MeasurementResource {
    private MeasurementService measurementService = new MeasurementService();

    @GET
    public Response calculateMeasurement(@QueryParam("clientId") int clientId) {
        Measurement measurement = measurementService.calculateMeasurement(clientId);
        return Response.status(OK).entity(measurement).build();
    }
}
