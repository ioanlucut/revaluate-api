package com.revaluate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.revaluate.core.bootstrap.ConfigProperties;
import io.dropwizard.setup.Environment;
import io.github.fallwizard.FallwizardApplication;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.message.filtering.EntityFilteringFeature;
import org.glassfish.jersey.server.ServerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.ws.rs.ext.Provider;
import java.util.EnumSet;
import java.util.Map;
import java.util.Properties;

public class RevaluateApplication extends FallwizardApplication<RevaluateConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FallwizardApplication.class);

    public static void main(String[] args) throws Exception {

        new RevaluateApplication().run(args);
    }

    @Override
    public void run(RevaluateConfiguration configuration, Environment environment) throws Exception {
        Properties properties = System.getProperties();
        LOGGER.info("----------INFO-----------");
        LOGGER.info(properties.getProperty(ConfigProperties.ENVIRONMENT));
        LOGGER.info("--------END INFO-----------");

        runMigrationScripts(configuration);

        super.run(configuration, environment);
        // The order is very important.
        setUpOtherOptions(environment);

        // Extra jackson configs.
        configureJackson(environment);

        // Configure CORS
        configureCORS(environment);

        // Register providers
        registerProviders(environment);
    }

    private void runMigrationScripts(RevaluateConfiguration configuration) {
        Flyway flyway = new Flyway();
        flyway.setValidateOnMigrate(Boolean.FALSE);
        flyway.setDataSource(configuration.getDataSourceFactory().getUrl(),
                configuration.getDataSourceFactory().getUser(),
                configuration.getDataSourceFactory().getPassword());

        try {
            flyway.migrate();
        } catch (FlywayException ex) {
            LOGGER.error(ex.getMessage(), ex);

            flyway.repair();
            flyway.migrate();
        }
    }

    private void configureJackson(Environment environment) {
        environment.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        environment.getObjectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        environment.getObjectMapper().disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        environment.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private void setUpOtherOptions(Environment environment) {
        // Entity filtering
        environment.jersey().register(EntityFilteringFeature.class);

        //-----------------------------------------------------------------
        // Register multipart feature - mandatory
        //-----------------------------------------------------------------
        environment.jersey().register(MultiPartFeature.class);

        // @ValidateOnExecution annotations on subclasses won't cause errors.
        environment.jersey().property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);
        environment.jersey().property(ServerProperties.PROCESSING_RESPONSE_ERRORS_ENABLED, true);
    }

    private void configureCORS(Environment environment) {
        FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_CREDENTIALS_HEADER, "true");
        filter.setInitParameter(CrossOriginFilter.CHAIN_PREFLIGHT_PARAM, "false");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_EXPOSE_HEADERS_HEADER, "true");
        filter.setInitParameter(CrossOriginFilter.EXPOSED_HEADERS_PARAM, "AuthToken");
    }

    private void registerProviders(Environment environment) {

        final Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(Provider.class);

        for (String beanName : beansWithAnnotation.keySet()) {

            Object provider = beansWithAnnotation.get(beanName);

            environment.jersey().register(provider);
            LOGGER.info("Registering provider : " + provider.getClass().getName());
        }
    }
}