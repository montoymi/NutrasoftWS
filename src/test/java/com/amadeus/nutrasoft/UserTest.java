package com.amadeus.nutrasoft;

import com.amadeus.nutrasoft.rest.UserResource;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
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
import java.net.URI;

import static org.junit.Assert.assertEquals;

public class UserTest extends JerseyTest {
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
    public void createUser() {
        String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        String input = xmlHeader + "<user></user>";

        WebTarget target = target().path("users");
        Response response = target.request().post(Entity.entity(input, MediaType.APPLICATION_XML));

        assertEquals(201, response.getStatus());

        System.out.println("Output received from web method createUser ....");
        System.out.println("User: " + response.readEntity(String.class));

        response.close();
    }

    //@Test
    public void updateUser() {
        String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        String input = xmlHeader + "<user></user>";

        WebTarget target = target().path("users");
        Response response = target.request().put(Entity.entity(input, MediaType.APPLICATION_XML));

        assertEquals(201, response.getStatus());

        System.out.println("Output received from web method updateUser ....");
        System.out.println("User: " + response.readEntity(String.class));

        response.close();
    }

    //@Test
    public void getUserByEmail() {
        WebTarget target = target().path("users").path("renzo@gmail.com");
        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());

        System.out.println("Output received from web method getUserByEmail ....");
        System.out.println("user: " + response.readEntity(String.class));

        response.close();
    }

    @ApplicationPath("/")
    public class MyResourceConfig extends ResourceConfig {
        MyResourceConfig() {
            super(UserResource.class);
        }
    }
}
