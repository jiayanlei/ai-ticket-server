package com.aiticket.server.ai;

import com.aiticket.server.ai.config.AiProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeepSeekChatClient {

    private static final String DEFAULT_BASE_URL = "https://api.deepseek.com";
    private static final String DEFAULT_CHAT_MODEL = "deepseek-chat";

    private final AiProperties properties;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public boolean isEnabled() {
        return Boolean.TRUE.equals(properties.getEnabled())
                && "deepseek".equalsIgnoreCase(properties.getProvider())
                && StringUtils.hasText(properties.getApiKey());
    }

    public String chat(String systemPrompt, String userMessage) {
        if (!isEnabled()) {
            throw new IllegalStateException("DeepSeek AI is not enabled");
        }

        try {
            String requestBody = objectMapper.writeValueAsString(Map.of(
                    "model", resolveChatModel(),
                    "stream", false,
                    "temperature", 0.3,
                    "messages", List.of(
                            Map.of("role", "system", "content", systemPrompt),
                            Map.of("role", "user", "content", userMessage)
                    )
            ));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(resolveBaseUrl() + "/chat/completions"))
                    .timeout(Duration.ofSeconds(45))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + properties.getApiKey())
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                log.warn("DeepSeek chat request failed, status={}, body={}", response.statusCode(), response.body());
                throw new IllegalStateException("DeepSeek request failed: " + response.statusCode());
            }

            return extractContent(response.body());
        } catch (IOException ex) {
            throw new IllegalStateException("DeepSeek response parse failed", ex);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("DeepSeek request interrupted", ex);
        }
    }

    private String extractContent(String body) throws IOException {
        JsonNode root = objectMapper.readTree(body);
        JsonNode content = root.path("choices").path(0).path("message").path("content");

        if (!content.isTextual() || !StringUtils.hasText(content.asText())) {
            throw new IllegalStateException("DeepSeek response content is empty");
        }

        return content.asText();
    }

    private String resolveBaseUrl() {
        String baseUrl = StringUtils.hasText(properties.getBaseUrl()) ? properties.getBaseUrl() : DEFAULT_BASE_URL;
        return baseUrl.replaceAll("/+$", "");
    }

    private String resolveChatModel() {
        return StringUtils.hasText(properties.getChatModel()) ? properties.getChatModel() : DEFAULT_CHAT_MODEL;
    }
}
