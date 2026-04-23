package com.aiticket.server.system.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "用户修改请求")
public class UserUpdateRequest {

    @NotBlank(message = "昵称不能为空")
    @Schema(description = "昵称", example = "张三")
    private String nickname;

    @Schema(description = "邮箱", example = "zhangsan@example.com")
    private String email;

    @Schema(description = "手机号", example = "13800000000")
    private String mobile;

    @Schema(description = "头像 URL", example = "https://example.com/avatar.png")
    private String avatar;

    @Schema(description = "部门 ID", example = "1")
    private Long deptId;

    @Schema(description = "状态", allowableValues = {"ENABLED", "DISABLED"}, example = "ENABLED")
    private String status;
}
