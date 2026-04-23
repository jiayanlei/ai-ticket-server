package com.aiticket.server.system.role.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "角色响应")
public class RoleVO {

    @Schema(description = "角色 ID", example = "1")
    private Long id;

    @Schema(description = "角色名称", example = "超级管理员")
    private String roleName;

    @Schema(description = "角色编码", example = "admin")
    private String roleCode;

    @Schema(description = "排序值，越小越靠前", example = "0")
    private Integer sortOrder;

    @Schema(description = "状态", allowableValues = {"ENABLED", "DISABLED"}, example = "ENABLED")
    private String status;

    @Schema(description = "备注", example = "系统内置管理员角色")
    private String remark;

    @Schema(description = "创建时间，格式 yyyy-MM-dd HH:mm:ss", example = "2026-04-23 09:00:00")
    private LocalDateTime createTime;

    @Schema(description = "更新时间，格式 yyyy-MM-dd HH:mm:ss", example = "2026-04-23 09:00:00")
    private LocalDateTime updateTime;
}
