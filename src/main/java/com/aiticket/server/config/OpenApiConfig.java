package com.aiticket.server.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    public static final String BEARER_AUTH = "BearerAuth";

    @Bean
    public OpenAPI aiTicketOpenApi() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(BEARER_AUTH, new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("Sa-Token")
                                .description("登录后复制 tokenValue 到这里，Swagger 会自动发送 Authorization: Bearer <token>")))
                .info(new Info()
                        .title("AI Ticket Server API")
                        .description("AI 智能工单分析平台后端接口文档")
                        .version("0.1.0")
                        .license(new License().name("Internal")));
    }
}
