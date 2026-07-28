package com.aiticket.server.ai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "ai")
public class AiProperties {

    private Boolean enabled = false;

    private String provider;

    private String baseUrl;

    private String apiKey;

    private String chatModel;

    private String embeddingModel;
}
