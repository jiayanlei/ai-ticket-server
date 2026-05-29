package com.aiticket.server.agent.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AiRiskLevelEnum {

    LOW("低风险"),
    MEDIUM("中风险"),
    HIGH("高风险");

    private final String description;
}
