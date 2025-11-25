package com.SanviiTechmet.h2dbcrud.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myCustomConfig(){
       return new OpenAPI().info(
               new Info().title("H2 DB CRUD App APIs")
                       .description("By Mayank Jaiswal")
       )
               .servers(List.of(new Server().url("http://localhost:8081").description("Local")
               ));
    }
}
