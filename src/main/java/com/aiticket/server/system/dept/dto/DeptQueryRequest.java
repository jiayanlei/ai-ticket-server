package com.aiticket.server.system.dept.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "部门查询参数")
public class DeptQueryRequest {

    @Schema(description = "部门名称，模糊匹配", example = "总部")
    private String deptName;

    @Schema(description = "部门编码，模糊匹配", example = "HQ")
    private String deptCode;

    @Schema(description = "状态", allowableValues = {"ENABLED", "DISABLED"}, example = "ENABLED")
    private String status;
}
