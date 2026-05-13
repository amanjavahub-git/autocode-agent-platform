package com.autocode.platform.config;


import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AutoCode Agent Platform API")
                        .version("1.0.0")
                        .description("API documentation for autonomous agents that read requirements and generate Spring Boot code.")
                        .contact(new Contact()
                                .name("AutoCode Technologies")
                                .email("support@autocode.com")
                                .url("https://autocode.com")));
    }
}
