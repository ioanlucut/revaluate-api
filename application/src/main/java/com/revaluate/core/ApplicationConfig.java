package com.revaluate.core;

import org.glassfish.jersey.message.filtering.EntityFilteringFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        // Register all resources present under the package.
        packages("com.revaluate");

        // Entity Data Filtering feature.
        register(EntityFilteringFeature.class);

        // Configure MOXy Json provider.
        register(new JsonConfiguration());

        // Validation.
        register(ValidationConfigurationContextResolver.class);

        // Entity filtering
        register(EntityFilteringFeature.class);

        // Now you can expect validation errors to be sent to the client.
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        // @ValidateOnExecution annotations on subclasses won't cause errors.
        property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);

        property(ServerProperties.PROCESSING_RESPONSE_ERRORS_ENABLED, true);
    }
}