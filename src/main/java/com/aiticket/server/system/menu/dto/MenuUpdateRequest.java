package com.aiticket.server.system.menu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "菜单修改请求")
public class MenuUpdateRequest {

    @Schema(description = "父级菜单 ID", example = "100")
    private Long parentId;

    @NotBlank(message = "菜单名称不能为空")
    @Schema(description = "菜单名称", example = "用户管理")
    private String menuName;

    @NotBlank(message = "菜单类型不能为空")
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
}
