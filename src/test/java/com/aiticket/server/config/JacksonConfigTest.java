package com.aiticket.server.config;

import com.aiticket.server.ticket.dto.TicketCreateRequest;
import com.aiticket.server.ticket.vo.TicketOrderVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class JacksonConfigTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        new JacksonConfig().jackson2ObjectMapperBuilderCustomizer().customize(builder);
        objectMapper = builder.build();
    }

    @Test
    void shouldDeserializeLocalDateTimeUsingProjectPattern() throws Exception {
        String json = """
                {
                  "title": "前端测试",
                  "description": "高速公路异常扣费",
                  "priority": "NORMAL",
                  "source": "WEB",
                  "category": "ETC扣费",
                  "applicantId": 1,
                  "applicantName": "管理员",
                  "dueTime": "2026-04-24 09:09:19"
                }
                """;

        TicketCreateRequest request = objectMapper.readValue(json, TicketCreateRequest.class);

        assertThat(request.getDueTime()).isEqualTo(LocalDateTime.of(2026, 4, 24, 9, 9, 19));
    }

    @Test
    void shouldDeserializeIsoLocalDateTimeForCompatibility() throws Exception {
        String json = """
                {
                  "title": "前端测试",
                  "description": "高速公路异常扣费",
                  "dueTime": "2026-04-24T09:09:19"
                }
                """;

        TicketCreateRequest request = objectMapper.readValue(json, TicketCreateRequest.class);

        assertThat(request.getDueTime()).isEqualTo(LocalDateTime.of(2026, 4, 24, 9, 9, 19));
    }

    @Test
    void shouldSerializeLocalDateTimeUsingProjectPattern() throws Exception {
        TicketOrderVO ticketOrderVO = new TicketOrderVO();
        ticketOrderVO.setDueTime(LocalDateTime.of(2026, 4, 24, 9, 9, 19));

        String json = objectMapper.writeValueAsString(ticketOrderVO);

        assertThat(json).contains("\"dueTime\":\"2026-04-24 09:09:19\"");
    }
}
