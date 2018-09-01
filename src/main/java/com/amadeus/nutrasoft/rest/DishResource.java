package com.amadeus.nutrasoft.rest;


import com.amadeus.nutrasoft.model.CalcDishParam;
import com.amadeus.nutrasoft.model.Dish;
import com.amadeus.nutrasoft.service.DishService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;

@Path("/")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class DishResource {
    private DishService dishService = new DishService();

    @POST
    @Path("dishes/calcs")
    public Response calculateDish(CalcDishParam calcDishParam) {
        dishService.calculateDish(calcDishParam);
        return Response.status(OK).entity(calcDishParam).build();
    }

    @POST
    @Path("dishes")
    public Response createDish(Dish dish) {
        dishService.createDish(dish);
        return Response.status(CREATED).entity(dish).build();
    }

    @PUT
    @Path("dishes/{id}")
    public Response updateDish(Dish dish) {
        dishService.updateDish(dish);
        return Response.status(OK).entity(dish).build();
    }

    @DELETE
    @Path("dishes/{id}")
    public Response deleteDish(@PathParam("id") int id) {
        dishService.deleteDish(id);
        return Response.status(OK).build();
    }

    @GET
    @Path("dishes/{id}")
    public Response getDishById(@PathParam("id") int id, @QueryParam("lang") String lang) {
        Dish dish = dishService.getDishById(id, lang);
        return Response.status(OK).entity(dish).build();
    }

    @GET
    @Path("coaches/{id}/dishes")
    public Response getDishesByCoachId(@PathParam("id") int coachId) {
        List<Dish> dishList = dishService.getDishesByCoachId(coachId);
        return Response.status(OK).entity(dishList).build();
    }
}
