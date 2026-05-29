package com.aiticket.server.agent.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AiTaskTypeEnum {

    NORMAL_CHAT("普通聊天"),
    FRONTEND_ERROR("前端报错分析"),
    BACKEND_API("后端接口新增"),
    BACKEND_LOG("后端日志分析"),
    SQL_GENERATE("生成 SQL"),
    FRONTEND_DEPLOY("前端构建发布"),
    BACKEND_DEPLOY("后端构建发布"),
    GIT_CHECK("Git 状态检查"),
    CHANGE_PLAN("生成修改计划");

    private final String description;

    public static AiTaskTypeEnum of(String taskType) {
        if (taskType == null || taskType.isBlank()) {
            return NORMAL_CHAT;
        }
        try {
            return AiTaskTypeEnum.valueOf(taskType.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return NORMAL_CHAT;
        }
    }
}
