package com.amadeus.nutrasoft.rest;


import com.amadeus.nutrasoft.model.Food;
import com.amadeus.nutrasoft.service.FoodService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.OK;

@Path("/foods")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class FoodResource {
    private FoodService foodService = new FoodService();

    @GET
    public Response getAllActivities(@QueryParam("lang") String lang) {
        List<Food> foodList = foodService.getAllFoods(lang);
        return Response.status(OK).entity(foodList).build();
    }
}
