package com.amadeus.nutrasoft.rest;


import com.amadeus.nutrasoft.model.DietType;
import com.amadeus.nutrasoft.model.Meal;
import com.amadeus.nutrasoft.model.Menu;
import com.amadeus.nutrasoft.service.MenuService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;

@Path("/")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class MenuResource {
    private MenuService menuService = new MenuService();

    @POST
    @Path("menus")
    public Response createMenu(Menu menu) {
        menuService.createMenu(menu);
        return Response.status(CREATED).entity(menu).build();
    }

    @PUT
    @Path("menus/{id}")
    public Response updateMenu(Menu menu) {
        menuService.updateMenu(menu);
        return Response.status(OK).entity(menu).build();
    }

    @DELETE
    @Path("menus/{id}")
    public Response deleteMenu(@PathParam("id") int id) {
        menuService.deleteMenu(id);
        return Response.status(OK).build();
    }

    @GET
    @Path("coaches/{id}/menus/next")
    public Response getNextMenuByCoachId(@PathParam("id") int coachId, @QueryParam("diet-type-id") int dietTypeId) {
        Menu menu = menuService.getNextMenuByCoachId(coachId, dietTypeId);
        return Response.status(OK).entity(menu).build();
    }

    @GET
    @Path("menus/{id}")
    public Response getMenuById(@PathParam("id") int id, @QueryParam("lang") String lang) {
        Menu menu = menuService.getMenuById(id, lang);
        return Response.status(OK).entity(menu).build();
    }

    @GET
    @Path("coaches/{id}/menus")
    public Response getDietTypesByCoachId(@PathParam("id") int coachId, @QueryParam("lang") String lang) {
        List<DietType> dietTypeList = menuService.getDietTypesByCoachId(coachId, lang);
        return Response.status(OK).entity(dietTypeList).build();
    }

    @GET
    @Path("/meals")
    public Response getAllMeals(@QueryParam("lang") String lang) {
        List<Meal> mealList = menuService.getAllMeals(lang);
        return Response.status(OK).entity(mealList).build();
    }
}
