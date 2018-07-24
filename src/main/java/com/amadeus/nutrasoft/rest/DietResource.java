package com.amadeus.nutrasoft.rest;

import com.amadeus.nutrasoft.model.DietType;
import com.amadeus.nutrasoft.model.Menu;
import com.amadeus.nutrasoft.model.Weight;
import com.amadeus.nutrasoft.service.DietService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.OK;

@Path("/diets")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class DietResource {
    private DietService dietService = new DietService();

    @GET
    public Response generateDiet(@QueryParam("clientId") int clientId, @QueryParam("week") byte week, @QueryParam("day") byte day, @QueryParam("lang")
            String lang) {
        Menu menu = dietService.generateDiet(clientId, week, day, lang);
        return Response.status(OK).entity(menu).build();
    }

    @GET
    @Path("/weights")
    public Response getWeightsByNdbno(@QueryParam("ndbno") String ndbno, @QueryParam("weight") float weight, @QueryParam("lang") String lang) {
        List<Weight> measureList = dietService.getWeightsByNdbno(ndbno, weight, lang);
        return Response.status(OK).entity(measureList).build();
    }

    @GET
    @Path("/diet-types")
    public Response getAllDietTypes(@QueryParam("lang") String lang) {
        List<DietType> itemList = dietService.getAllDietTypes(lang);
        return Response.status(OK).entity(itemList).build();
    }
}
