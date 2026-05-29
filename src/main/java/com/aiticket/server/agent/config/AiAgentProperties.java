package com.aiticket.server.agent.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "ai.agent")
public class AiAgentProperties {

    private String frontendPath = "/Users/lei/Desktop/ai-ticket-web";

    private String backendPath = "/Users/lei/Desktop/ai-ticket-server";

    private String logPath = "./logs";

    private Boolean enableCommandExec = false;

    private Boolean enableFileWrite = false;

    private Boolean enableRealDeploy = false;
}
