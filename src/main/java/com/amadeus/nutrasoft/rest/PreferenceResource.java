package com.amadeus.nutrasoft.rest;

import com.amadeus.nutrasoft.model.Preference;
import com.amadeus.nutrasoft.service.PreferenceService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.OK;

@Path("/")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class PreferenceResource {
    private PreferenceService preferenceService = new PreferenceService();

    @PUT
    @Path("preferences/{id}")
    public Response updatePreference(Preference preference) {
        preferenceService.updatePreference(preference);
        return Response.status(OK).entity(preference).build();
    }

    @GET
    @Path("users/{id}/preferences")
    public Response getPreferenceByUserId(@PathParam("id") int userId, @QueryParam("lang") String lang) {
        Preference preference = preferenceService.getPreferenceByUserId(userId, lang);
        return Response.status(OK).entity(preference).build();
    }
}