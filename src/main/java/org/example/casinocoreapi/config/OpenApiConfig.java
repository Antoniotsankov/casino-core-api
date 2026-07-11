package org.example.casinocoreapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI casinoCoreApi() {

        return new OpenAPI()
                .info(new Info()
                        .title("Casino Core API")
                        .description("Backend API for managing users, wallets and casino transactions.")
                        .version("1.0.0"));
    }
}