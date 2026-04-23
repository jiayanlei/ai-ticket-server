package com.aiticket.server.system.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "用户新增请求")
public class UserCreateRequest {

    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名，唯一", example = "zhangsan")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 64, message = "密码长度应为6-64位")
    @Schema(description = "密码，6-64 位", example = "admin123")
    private String password;

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

    @Schema(description = "状态，默认 ENABLED", allowableValues = {"ENABLED", "DISABLED"}, example = "ENABLED")
    private String status;
}
