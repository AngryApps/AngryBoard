package br.com.angryapps.api;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

@ApplicationPath("/")
public class ApiApplication extends ResourceConfig {

    public ApiApplication() {
        packages("br.com.angryapps.api.resources");

        property(ServerProperties.WADL_FEATURE_DISABLE, true);
    }
}
