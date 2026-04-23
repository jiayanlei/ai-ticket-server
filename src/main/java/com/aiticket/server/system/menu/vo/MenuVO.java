package com.aiticket.server.system.menu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "菜单响应")
public class MenuVO {

    @Schema(description = "菜单 ID", example = "110")
    private Long id;

    @Schema(description = "父级菜单 ID", example = "100")
    private Long parentId;

    @Schema(description = "菜单名称", example = "用户管理")
    private String menuName;

    @Schema(description = "菜单类型", allowableValues = {"DIR", "MENU", "BUTTON"}, example = "MENU")
    private String menuType;

    @Schema(description = "路由地址", example = "/system/users")
    private String path;

    @Schema(description = "前端组件路径", example = "system/user/index")
    private String component;

    @Schema(description = "权限标识", example = "system:user:list")
    private String perms;

    @Schema(description = "图标", example = "user")
    private String icon;

    @Schema(description = "排序值，越小越靠前", example = "1")
    private Integer sortOrder;

    @Schema(description = "是否在菜单中显示", example = "true")
    private Boolean visible;

    @Schema(description = "状态", allowableValues = {"ENABLED", "DISABLED"}, example = "ENABLED")
    private String status;

    @Schema(description = "创建时间，格式 yyyy-MM-dd HH:mm:ss", example = "2026-04-23 09:00:00")
    private LocalDateTime createTime;

    @Schema(description = "更新时间，格式 yyyy-MM-dd HH:mm:ss", example = "2026-04-23 09:00:00")
    private LocalDateTime updateTime;
}
