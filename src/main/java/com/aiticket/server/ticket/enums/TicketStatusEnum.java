package com.aiticket.server.ticket.enums;

import com.aiticket.server.common.exception.BusinessException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TicketStatusEnum {

    DRAFT("草稿"),
    PENDING_ACCEPT("待受理"),
    ACCEPTED("已受理"),
    PROCESSING("处理中"),
    PENDING("挂起中"),
    WAIT_CONFIRM("待确认"),
    COMPLETED("已完成"),
    CLOSED("已关闭"),
    REJECTED("已驳回");

    private final String description;

    TicketStatusEnum(String description) {
        this.description = description;
    }

    public String getCode() {
        return name();
    }

    public static TicketStatusEnum of(String code) {
        if ("NEW".equals(code)) {
            return DRAFT;
        }
        if ("RESOLVED".equals(code)) {
            return WAIT_CONFIRM;
        }
        return Arrays.stream(values())
                .filter(status -> status.name().equals(code))
                .findFirst()
                .orElseThrow(() -> new BusinessException("工单状态不存在"));
    }
}
