package br.com.angryapps.api;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.validation.ValidationFeature;

@ApplicationPath("/")
public class ApiApplication extends ResourceConfig {

    public ApiApplication() {
        packages("br.com.angryapps.api.resources", "br.com.angryapps.api.providers");

        register(ValidationFeature.class);

        // Configure validation
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);

        property(ServerProperties.WADL_FEATURE_DISABLE, true);
    }
}
