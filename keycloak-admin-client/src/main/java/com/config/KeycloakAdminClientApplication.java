package com.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(
        servers = @Server(url = "http://localhost:8081"),
        info = @Info(title = "Keycloak Admin Client",
        version = "v1",
        description = "Keycloak Admin Client"))
public class KeycloakAdminClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(KeycloakAdminClientApplication.class, args);
    }
}