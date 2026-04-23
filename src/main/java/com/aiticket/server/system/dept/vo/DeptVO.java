package com.aiticket.server.system.dept.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "部门响应")
public class DeptVO {

    @Schema(description = "部门 ID", example = "1")
    private Long id;

    @Schema(description = "父级部门 ID", example = "0")
    private Long parentId;

    @Schema(description = "部门名称", example = "总部")
    private String deptName;

    @Schema(description = "部门编码", example = "HQ")
    private String deptCode;

    @Schema(description = "负责人", example = "admin")
    private String leader;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "排序值，越小越靠前", example = "0")
    private Integer sortOrder;

    @Schema(description = "状态", allowableValues = {"ENABLED", "DISABLED"}, example = "ENABLED")
    private String status;

    @Schema(description = "创建时间，格式 yyyy-MM-dd HH:mm:ss", example = "2026-04-23 09:00:00")
    private LocalDateTime createTime;

    @Schema(description = "更新时间，格式 yyyy-MM-dd HH:mm:ss", example = "2026-04-23 09:00:00")
    private LocalDateTime updateTime;
}
