package com.cinema.cinema.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Cinema", version = "${app.version}", description = "Cinema API documentation"))
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi allOpenApi() {
        String[] packages = {"com.cinema.cinema"};
        return GroupedOpenApi.builder().group("all").packagesToScan(packages).build();
    }

}
