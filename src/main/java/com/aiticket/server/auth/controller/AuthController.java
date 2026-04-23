package com.aiticket.server.auth.controller;

import com.aiticket.server.auth.dto.LoginRequest;
import com.aiticket.server.auth.service.AuthService;
import com.aiticket.server.auth.vo.LoginVO;
import com.aiticket.server.common.core.ApiResponse;
import com.aiticket.server.config.OpenApiConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "认证接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "登录", description = "用户名密码登录，成功后返回 Sa-Token tokenValue。前端后续请求需要传 Authorization: Bearer <tokenValue>。")
    @PostMapping("/login")
    public ApiResponse<LoginVO> login(@Valid @RequestBody LoginRequest request, HttpServletRequest servletRequest) {
        return ApiResponse.ok(authService.login(request, servletRequest));
    }

    @Operation(summary = "退出登录", description = "注销当前 token。")
    @SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
    @PostMapping("/logout")
    public ApiResponse<Void> logout() {
        authService.logout();
        return ApiResponse.ok();
    }


    @GetMapping("/test/pwd")
    public String testPwd() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode("admin123");
    }

    @Operation(summary = "当前登录用户", description = "返回当前用户、角色编码与权限编码。")
    @SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
    @GetMapping("/me")
    public ApiResponse<LoginVO> currentUser() {
        return ApiResponse.ok(authService.currentUser());
    }
}
