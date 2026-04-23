package com.aiticket.server.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "登录响应")
public class LoginVO {

    @Schema(description = "用户 ID", example = "1")
    private Long userId;

    @Schema(description = "用户名", example = "admin")
    private String username;

    @Schema(description = "用户昵称", example = "系统管理员")
    private String nickname;

    @Schema(description = "令牌名称", example = "Authorization")
    private String tokenName;

    @Schema(description = "令牌值，前端请求时拼成 Authorization: Bearer <tokenValue>")
    private String tokenValue;

    @Schema(description = "令牌前缀", example = "Bearer")
    private String tokenPrefix;

    @Schema(description = "角色编码", example = "[\"admin\"]")
    private List<String> roles;

    @Schema(description = "权限编码", example = "[\"*\",\"*:*:*\"]")
    private List<String> permissions;
}
