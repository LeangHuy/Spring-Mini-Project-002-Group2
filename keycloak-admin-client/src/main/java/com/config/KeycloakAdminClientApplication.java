package com.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(
        servers = @Server(url = "/"),
        info = @Info(title = "Keycloak Admin Client",
        version = "v1",
        description = "Keycloak Admin Client"))
@SecurityScheme(
        name = "mini-project-2",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(
                clientCredentials = @OAuthFlow(
                        tokenUrl = "http://localhost:8080/realms/mini-project-2/protocol/openid-connect/token"
                )
        )
)
public class KeycloakAdminClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(KeycloakAdminClientApplication.class, args);
    }

}