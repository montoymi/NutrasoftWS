package com.amadeus.nutrasoft.rest;

import com.amadeus.nutrasoft.commons.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

@Path("/")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class FileResource {
    @POST
    @Path("files")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response writeFile(@FormDataParam("file") InputStream inputStream, @FormDataParam("file") FormDataContentDisposition fileMetaData) {
        String name = fileMetaData.getFileName();
        FileUtils.writeFile(inputStream, name);
        return Response.ok(name).build();
    }
}
