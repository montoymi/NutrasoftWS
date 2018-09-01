package com.amadeus.nutrasoft;

import com.amadeus.nutrasoft.rest.FileResource;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.File;
import java.net.URI;

import static org.junit.Assert.assertEquals;

public class FileTest extends JerseyTest {
    @Override
    protected Application configure() {
        return new MyResourceConfig();
    }

    @Override
    protected void configureClient(ClientConfig config) {
        config.register(MultiPartFeature.class);
    }

    @Override
    protected URI getBaseUri() {
        return UriBuilder.fromUri("http://localhost:8080").path("nutrasoft-ws").build();
    }

    //@Test
    public void writeFile() {
        String path = "/Users/montoymi/Pictures/Nutrasoft/Files/";
        String name = "profile1.jpg";
        String pathname = path + name;

        FileDataBodyPart filePart = new FileDataBodyPart("file", new File(pathname));
        filePart.setContentDisposition(FormDataContentDisposition.name("file").fileName(name).build());
        filePart.setMediaType(MediaType.APPLICATION_OCTET_STREAM_TYPE);
        MultiPart multiPart = new FormDataMultiPart().bodyPart(filePart);

        WebTarget target = target().path("files");
        Response response = target.request().post(Entity.entity(multiPart, MediaType.MULTIPART_FORM_DATA_TYPE));

        assertEquals(200, response.getStatus());

        System.out.println("Output received from web method writeFile ....");
        System.out.println("File uploaded: " + response.readEntity(String.class));

        response.close();
    }

    @ApplicationPath("/")
    public class MyResourceConfig extends ResourceConfig {
        MyResourceConfig() {
            super(FileResource.class, MultiPartFeature.class);
        }
    }
}
