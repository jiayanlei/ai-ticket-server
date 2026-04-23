package com.aiticket.server.system.role.dto;

import com.aiticket.server.common.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "角色查询参数")
public class RoleQueryRequest extends PageQuery {

    @Schema(description = "角色名称，模糊匹配", example = "管理员")
    private String roleName;

    @Schema(description = "角色编码，模糊匹配", example = "admin")
    private String roleCode;

    @Schema(description = "状态", allowableValues = {"ENABLED", "DISABLED"}, example = "ENABLED")
    private String status;
}
