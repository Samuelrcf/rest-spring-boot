package br.edu.ufersa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info().title("RESTful API with Spring Boot").version("v1").description("").termsOfService("").license(new License().name("Apache 2.0").url("")));
		
	}
    // http://localhost:8080/swagger-ui/index.html
}
