package com.aiticket.server.system.dept.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "部门修改请求")
public class DeptUpdateRequest {

    @Schema(description = "父级部门 ID", example = "0")
    private Long parentId;

    @NotBlank(message = "部门名称不能为空")
    @Schema(description = "部门名称", example = "研发部")
    private String deptName;

    @NotBlank(message = "部门编码不能为空")
    @Schema(description = "部门编码", example = "RD")
    private String deptCode;

    @Schema(description = "负责人", example = "张三")
    private String leader;

    @Schema(description = "联系电话", example = "13800000000")
    private String phone;

    @Schema(description = "邮箱", example = "rd@example.com")
    private String email;

    @Schema(description = "排序值，越小越靠前", example = "1")
    private Integer sortOrder;

    @Schema(description = "状态", allowableValues = {"ENABLED", "DISABLED"}, example = "ENABLED")
    private String status;
}
