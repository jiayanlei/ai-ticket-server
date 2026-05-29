package com.aiticket.server.agent.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AiFileChangeTypeEnum {

    CHECK("检查"),
    CREATE("新增"),
    UPDATE("修改"),
    DELETE("删除");

    private final String description;
}
