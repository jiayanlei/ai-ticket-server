package com.aiticket.server.system.loginlog.entity;

import com.aiticket.server.common.core.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_login_log")
@Schema(description = "登录日志")
public class SysLoginLog extends BaseEntity {

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "登录IP")
    private String loginIp;

    @Schema(description = "User-Agent")
    private String userAgent;

    @Schema(description = "登录状态")
    private String status;

    @Schema(description = "消息")
    private String message;

    @Schema(description = "登录时间")
    private LocalDateTime loginTime;
}
