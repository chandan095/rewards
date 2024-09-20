package com.reward.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-api")
                .pathsToMatch("/**")
                .build();
    }
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Reward Application")
                        .description("Reward Application")
                        .version("1.0")
                        .termsOfService("Terms of service")
                        .contact(new Contact()
                                .name("Chandan Prasad")
                                .url("https://www.test.com")
                                .email("chandankumarprasad1@gmail.com"))
                        .license(new License()
                                .name("License of API")
                                .url("https://www.test.com/api-license")));
    }
}
