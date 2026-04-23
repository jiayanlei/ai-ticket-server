package com.aiticket.server.system.menu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "菜单查询参数")
public class MenuQueryRequest {

    @Schema(description = "菜单名称，模糊匹配", example = "用户管理")
    private String menuName;

    @Schema(description = "菜单类型", allowableValues = {"DIR", "MENU", "BUTTON"}, example = "MENU")
    private String menuType;

    @Schema(description = "状态", allowableValues = {"ENABLED", "DISABLED"}, example = "ENABLED")
    private String status;
}
