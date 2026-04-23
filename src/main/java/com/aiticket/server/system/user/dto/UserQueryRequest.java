package com.aiticket.server.system.user.dto;

import com.aiticket.server.common.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户查询参数")
public class UserQueryRequest extends PageQuery {

    @Schema(description = "用户名，模糊匹配", example = "admin")
    private String username;

    @Schema(description = "昵称，模糊匹配", example = "系统管理员")
    private String nickname;

    @Schema(description = "部门 ID", example = "1")
    private Long deptId;

    @Schema(description = "状态", allowableValues = {"ENABLED", "DISABLED"}, example = "ENABLED")
    private String status;
}
