package dev.yogizogi.global.config.openApi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {

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


    private Info getInfo() {
        return new Info()
                .title("YOGIZOGI REST API")
                .description("YOGIZOGI API DOCS");
    }

//    private OpenApiCustomiser buildSecurityOpenApi() {
//        SecurityScheme securityScheme = new SecurityScheme()
//                .name("Authorization")
//                .type(SecurityScheme.Type.HTTP)
//                .in(SecurityScheme.In.HEADER)
//                .bearerFormat("JWT")
//                .scheme("bearer");
//
//        return OpenApi -> OpenApi
//                .addSecurityItem(new SecurityRequirement().addList("Authorization"))
//                .getComponents().addSecuritySchemes("Authorization", securityScheme);
//    }

}

