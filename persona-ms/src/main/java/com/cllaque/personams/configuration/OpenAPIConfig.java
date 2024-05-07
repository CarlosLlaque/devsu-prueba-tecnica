package com.cllaque.personams.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI getOpenAPIDocumentation(){
        return new OpenAPI();
    }
    
}
