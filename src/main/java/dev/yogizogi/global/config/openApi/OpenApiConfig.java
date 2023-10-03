package dev.yogizogi.global.config.openApi;

import static dev.yogizogi.global.common.model.constant.Format.TOKEN_HEADER_NAME;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Arrays;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi() {

        Server devServer = new Server();
        devServer.setDescription("dev");
        devServer.setUrl("https://dev.yogizogi.store");

        Server localServer = new Server();
        localServer.setDescription("local");
        localServer.setUrl("http://localhost:8081");


        return new OpenAPI()
                .info(getInfo())
                .servers(Arrays.asList(devServer, localServer));

    }

    @Bean
    public GroupedOpenApi securityGroup() {
        return GroupedOpenApi
                .builder()
                .group("토큰 필요 API")
                .pathsToExclude(
                        "/api/auth/**",
                        "/api/sign-up/**",
                        "/api/verification/**",
                        "/api/users/find-password",
                        "/api/users/update-password"
                )
                .addOpenApiCustomizer(buildSecurityOpenApi())
                .build();
    }

    @Bean
    public GroupedOpenApi nonSecurityGroup() {
        return GroupedOpenApi
                .builder()
                .group("토큰 불필요 API")
                .pathsToMatch(
                        "/api/auth/**",
                        "/api/sign-up/**",
                        "/api/verification/**",
                        "/api/users/find-password",
                        "/api/users/update-password"
                )
                .build();
    }

    private Info getInfo() {
        return new Info()
                .title("YOGIZOGI REST API")
                .description("YOGIZOGI API DOCS");
    }

    private OpenApiCustomizer buildSecurityOpenApi() {
        SecurityScheme securityScheme = new SecurityScheme()
                .name(TOKEN_HEADER_NAME)
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .bearerFormat("JWT")
                .scheme("Bearer");

        return openApi -> openApi
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(TOKEN_HEADER_NAME)
                )
                .getComponents()
                .addSecuritySchemes(TOKEN_HEADER_NAME, securityScheme);
    }

}

