package com.amadeus.nutrasoft.rest;

import com.amadeus.nutrasoft.model.Compliance;
import com.amadeus.nutrasoft.model.Meal;
import com.amadeus.nutrasoft.model.Plan;
import com.amadeus.nutrasoft.model.User;
import com.amadeus.nutrasoft.service.ComplianceService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.OK;

@Path("/")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class ComplianceResource {
    private ComplianceService complianceService = new ComplianceService();

    @POST
    @Path("compliances")
    public Response createCompliance(Compliance compliance) {
        complianceService.createCompliance(compliance);
        return Response.status(OK).entity(compliance).build();
    }

    @PUT
    @Path("compliances/{id}")
    public Response updateCompliance(Compliance compliance) {
        complianceService.updateCompliance(compliance);
        return Response.status(OK).entity(compliance).build();
    }

    @GET
    @Path("compliances/{id}")
    public Response getComplianceById(@PathParam("id") int userId, @QueryParam("lang") String lang) {
        Compliance compliance = complianceService.getComplianceById(userId, lang);
        return Response.status(OK).entity(compliance).build();
    }

    @GET
    @Path("clients/{id}/compliances/last")
    public Response getPlanByClientId(@PathParam("id") int clientId, @QueryParam("lang") String lang) {
        Plan plan = complianceService.getPlanByClientId(clientId, lang);
        return Response.status(OK).entity(plan).build();
    }

    @GET
    @Path("coaches/{id}/compliances")
    public Response getClientsByCoachId(@PathParam("id") int coachId) {
        List<User> clientList = complianceService.getClientsByCoachId(coachId);
        return Response.status(OK).entity(clientList).build();
    }

    @GET
    @Path("clients/{id}/meals")
    public Response getMealsByClientId(@PathParam("id") int clientId, @QueryParam("lang") String lang) {
        List<Meal> mealList = complianceService.getMealsByClientId(clientId, lang);
        return Response.status(OK).entity(mealList).build();
    }
}