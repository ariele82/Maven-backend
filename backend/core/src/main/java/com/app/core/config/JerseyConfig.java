package com.app.core.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.inject.hk2.Hk2InjectionManagerFactory;

public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(Hk2InjectionManagerFactory.class);
        register(JacksonFeature.class);
        packages("com.app.core.resource");
    }
}
