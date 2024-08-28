package com.gabrielmotta.userscore.api.config;

import com.gabrielmotta.userscore.infra.properties.AppProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@SecurityScheme(
    name = "Auth: jwt",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer"
)
@OpenAPIDefinition(security = @SecurityRequirement(name = "Auth: jwt"))
@RequiredArgsConstructor
public class SwaggerConfig implements WebMvcConfigurer {

    private final AppProperties properties;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/api/docs", "/swagger-ui.html");
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
            .group("admin")
            .pathsToMatch("/**")
            .build();
    }

    @Bean
    public OpenAPI springShopOpenApi() {
        return new OpenAPI()
            .info(new Info().title(properties.name())
                .description("User Score API Documentation")
                .version(properties.version()));
    }
}
