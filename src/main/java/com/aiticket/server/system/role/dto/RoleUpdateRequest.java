package com.aiticket.server.system.role.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "角色修改请求")
public class RoleUpdateRequest {

    @NotBlank(message = "角色名称不能为空")
    @Schema(description = "角色名称", example = "运维工程师")
    private String roleName;

    @NotBlank(message = "角色编码不能为空")
    @Schema(description = "角色编码", example = "ops")
    private String roleCode;

    @Schema(description = "排序值，越小越靠前", example = "1")
    private Integer sortOrder;

    @Schema(description = "状态", allowableValues = {"ENABLED", "DISABLED"}, example = "ENABLED")
    private String status;

    @Schema(description = "备注", example = "负责工单处理")
    private String remark;
}
