package com.amadeus.nutrasoft;

import com.amadeus.nutrasoft.filters.CORSResponseFilter;
import org.glassfish.jersey.server.ResourceConfig;

public class MyResourceConfig extends ResourceConfig {
    public MyResourceConfig() {
        register(CORSResponseFilter.class);
    }
}
