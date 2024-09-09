package org.example.user;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@ConfigurationPropertiesScan
@EnableR2dbcAuditing
@EnableR2dbcRepositories
@OpenAPIDefinition(
        info =
        @Info(
                title = "Lamenu Order Service v1.0",
                version = "1.0",
                description = "Lamenu Order Service v1.0 Documentation"),
        security = {@SecurityRequirement(name = "bearerAuth")},
        servers = {
                @Server(url = "https://api-dev.lamenu.uz/", description = "Default Server URL"),
                @Server(url = "http://localhost:8103/", description = "Local Server URL")
        })
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER)
@SpringBootApplication
@ComponentScan("org.example")
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
