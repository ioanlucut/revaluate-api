package com.revaluate;

import io.dropwizard.setup.Environment;
import io.github.fallwizard.FallwizardApplication;
import io.github.fallwizard.configuration.FallwizardConfiguration;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.message.filtering.EntityFilteringFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.ws.rs.ext.Provider;
import java.util.EnumSet;
import java.util.Map;

public class RevaluateApplication extends FallwizardApplication<FallwizardConfiguration> {

    public static void main(String[] args) throws Exception {

        new RevaluateApplication().run(args);
    }

    @Override
    public void run(FallwizardConfiguration configuration, Environment environment) throws Exception {
        super.run(configuration, environment);

        // Configure CORS
        configureCors(environment);

        // Entity filtering
        environment.jersey().getResourceConfig().register(EntityFilteringFeature.class);

        registerProviders(environment);
    }

    private void configureCors(Environment environment) {
        FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("allowCredentials", "true");
    }

    private static final Logger loggerx = LoggerFactory.getLogger(FallwizardApplication.class);

    private void registerProviders(Environment environment) {

        final Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(Provider.class);

        for (String beanName : beansWithAnnotation.keySet()) {

            Object provider = beansWithAnnotation.get(beanName);

            environment.jersey().register(provider);
            loggerx.info("Registering provider : " + provider.getClass().getName());
        }
    }
}