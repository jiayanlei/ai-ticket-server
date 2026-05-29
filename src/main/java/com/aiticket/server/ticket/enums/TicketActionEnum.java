package com.aiticket.server.ticket.enums;

import lombok.Getter;

@Getter
public enum TicketActionEnum {

    CREATE_DRAFT("创建草稿"),
    SUBMIT("提交工单"),
    ACCEPT("立即受理"),
    START_PROCESS("开始处理"),
    SUSPEND("挂起工单"),
    RESUME("恢复处理"),
    FINISH("处理完成"),
    CONFIRM("确认完成"),
    REOPEN("重新打开"),
    TRANSFER("转派工单"),
    REJECT("驳回工单"),
    CLOSE("关闭工单"),
    AI_ANALYZE("AI 分析");

    private final String description;

    TicketActionEnum(String description) {
        this.description = description;
    }

    public String getCode() {
        return name();
    }
}
