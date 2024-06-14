package com.billybang.propertyservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "BillyBang Property Service API",
                description = "BillyBang 에서 개발 중인 매물 서비스 API 문서",
                version = "v1"))
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi propertiesOpenApi() {
        String[] paths = {"/properties/**"};

        return GroupedOpenApi.builder()
                .group("Property Service API")
                .pathsToMatch(paths)
                .build();
    }
}
