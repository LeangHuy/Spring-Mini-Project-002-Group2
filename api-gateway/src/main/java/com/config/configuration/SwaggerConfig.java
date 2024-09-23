package com.config.configuration;

import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public CommandLineRunner commandLineRunner(SwaggerUiConfigParameters swaggerUiConfigParameters) {
        return args -> {
            swaggerUiConfigParameters.addGroup("keycloak-admin-client");
            swaggerUiConfigParameters.addGroup("task-service");
        };
    }
}
