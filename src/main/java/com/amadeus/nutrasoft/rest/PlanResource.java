package com.amadeus.nutrasoft.rest;

import com.amadeus.nutrasoft.model.Activity;
import com.amadeus.nutrasoft.model.Item;
import com.amadeus.nutrasoft.model.Plan;
import com.amadeus.nutrasoft.model.User;
import com.amadeus.nutrasoft.service.PlanService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;

@Path("/")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class PlanResource {
    private PlanService planService = new PlanService();

    @POST
    @Path("plans/calcs")
    public Response calculatePlan(Plan plan) {
        planService.calculatePlan(plan);
        return Response.status(OK).entity(plan).build();
    }

    @POST
    @Path("plans")
    public Response createPlan(Plan plan) {
        planService.createPlan(plan);
        return Response.status(CREATED).entity(plan).build();
    }

    @PUT
    @Path("plans/{id}")
    public Response updatePlan(Plan plan) {
        planService.updatePlan(plan);
        return Response.status(OK).entity(plan).build();
    }

    @DELETE
    @Path("plans/{id}")
    public Response deletePlan(@PathParam("id") int id) {
        planService.deletePlan(id);
        return Response.status(OK).build();
    }

    @GET
    @Path("plans/{id}")
    public Response getPlanById(@PathParam("id") int id, @QueryParam("lang") String lang) {
        Plan plan = planService.getPlanById(id, lang);
        return Response.status(OK).entity(plan).build();
    }

    @GET
    @Path("clients/{id}/plans/last")
    public Response getPlanByClientId(@PathParam("id") int clientId, @QueryParam("lang") String lang) {
        Plan plan = planService.getPlanByClientId(clientId, lang);
        return Response.status(OK).entity(plan).build();
    }

    @GET
    @Path("coaches/{id}/plans")
    public Response getClientsByCoachId(@PathParam("id") int coachId, @QueryParam("lang") String lang) {
        List<User> clientList = planService.getClientsByCoachId(coachId, lang);
        return Response.status(OK).entity(clientList).build();
    }

    @GET
    @Path("goals")
    public Response getAllGoals(@QueryParam("lang") String lang) {
        List<Item> itemList = planService.getAllGoals(lang);
        return Response.status(OK).entity(itemList).build();
    }

    @GET
    @Path("activities")
    public Response getAllActivities(@QueryParam("lang") String lang) {
        List<Activity> activityList = planService.getAllActivities(lang);
        return Response.status(OK).entity(activityList).build();
    }

    @GET
    @Path("activities/default")
    public Response getDefaultActivities(@QueryParam("lang") String lang) {
        List<Activity> activityList = planService.getDefaultActivities(lang);
        return Response.status(OK).entity(activityList).build();
    }
}