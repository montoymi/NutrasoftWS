package com.amadeus.nutrasoft.filters;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;

public class CORSResponseFilter implements ContainerResponseFilter {
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();
        // The  “*” means the request can come from any domain – this is the way to set this header if you want to make your REST API public where
        // everyone can access it
        headers.add("Access-Control-Allow-Origin", "*");
        // Methods are allowed when accessing the resource
        headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        // This headers can be used when making the actual request
        headers.add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-Codingpedia");
    }
}