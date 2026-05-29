package com.aiticket.server.ticket.enums;

import lombok.Getter;

@Getter
public enum TicketPriorityEnum {

    LOW("低", 72),
    NORMAL("普通", 24),
    HIGH("高", 8),
    URGENT("紧急", 4);

    private final String description;
    private final int slaHours;

    TicketPriorityEnum(String description, int slaHours) {
        this.description = description;
        this.slaHours = slaHours;
    }

    public String getCode() {
        return name();
    }

    public static TicketPriorityEnum ofNullable(String code) {
        if (code == null || code.isBlank()) {
            return NORMAL;
        }
        for (TicketPriorityEnum priority : values()) {
            if (priority.name().equals(code)) {
                return priority;
            }
        }
        return NORMAL;
    }
}
