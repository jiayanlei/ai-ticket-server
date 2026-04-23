package com.aiticket.server.system.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "用户响应")
public class UserVO {

    @Schema(description = "用户 ID", example = "1")
    private Long id;

    @Schema(description = "用户名", example = "admin")
    private String username;

    @Schema(description = "昵称", example = "系统管理员")
    private String nickname;

    @Schema(description = "邮箱", example = "admin@example.com")
    private String email;

    @Schema(description = "手机号", example = "13800000000")
    private String mobile;

    @Schema(description = "头像 URL")
    private String avatar;

    @Schema(description = "部门 ID", example = "1")
    private Long deptId;

    @Schema(description = "状态", allowableValues = {"ENABLED", "DISABLED"}, example = "ENABLED")
    private String status;

    @Schema(description = "最后登录时间，格式 yyyy-MM-dd HH:mm:ss", example = "2026-04-23 09:00:00")
    private LocalDateTime lastLoginTime;

    @Schema(description = "创建时间，格式 yyyy-MM-dd HH:mm:ss", example = "2026-04-23 09:00:00")
    private LocalDateTime createTime;

    @Schema(description = "更新时间，格式 yyyy-MM-dd HH:mm:ss", example = "2026-04-23 09:00:00")
    private LocalDateTime updateTime;
}
