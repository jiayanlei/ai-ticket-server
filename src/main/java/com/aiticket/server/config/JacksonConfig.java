package com.aiticket.server.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Configuration
public class JacksonConfig {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder
                .serializers(new LocalDateTimeSerializer(DEFAULT_DATE_TIME_FORMATTER))
                .deserializers(flexibleLocalDateTimeDeserializer());
    }

    private JsonDeserializer<LocalDateTime> flexibleLocalDateTimeDeserializer() {
        return new LocalDateTimeDeserializer(DEFAULT_DATE_TIME_FORMATTER) {
            @Override
            public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
                String value = parser.getValueAsString();
                if (value == null || value.isBlank()) {
                    return null;
                }
                try {
                    return LocalDateTime.parse(value, DEFAULT_DATE_TIME_FORMATTER);
                } catch (DateTimeParseException ignored) {
                    try {
                        return LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    } catch (DateTimeParseException ex) {
                        throw InvalidFormatException.from(parser,
                                "日期时间格式错误，请使用 " + DATE_TIME_PATTERN + " 或 ISO-8601 格式",
                                value,
                                LocalDateTime.class);
                    }
                }
            }
        };
    }
}
