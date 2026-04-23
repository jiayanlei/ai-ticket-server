package com.aiticket.server.system.user.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.aiticket.server.common.core.ApiResponse;
import com.aiticket.server.common.page.PageResult;
import com.aiticket.server.config.OpenApiConfig;
import com.aiticket.server.system.user.dto.UserCreateRequest;
import com.aiticket.server.system.user.dto.UserQueryRequest;
import com.aiticket.server.system.user.dto.UserUpdateRequest;
import com.aiticket.server.system.user.service.SysUserService;
import com.aiticket.server.system.user.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@Tag(name = "用户管理")
@SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/users")
public class SysUserController {

    private final SysUserService userService;

    @SaCheckPermission("system:user:list")
    @Operation(summary = "用户分页列表", description = "按用户名、昵称、部门、状态筛选用户，返回统一分页结构。")
    @GetMapping
    public ApiResponse<PageResult<UserVO>> list(@ParameterObject @Valid UserQueryRequest request) {
        return ApiResponse.ok(userService.pageUsers(request));
    }




    @SaCheckPermission("system:user:add")
    @Operation(summary = "新增用户", description = "创建用户并返回新用户 ID。username 必须唯一，password 会服务端加密。")
    @PostMapping
    public ApiResponse<Long> create(@Valid @RequestBody UserCreateRequest request) {
        return ApiResponse.ok(userService.createUser(request));
    }

    @SaCheckPermission("system:user:query")
    @Operation(summary = "用户详情", description = "根据用户 ID 查询用户详情。")
    @GetMapping("/{id}")
    public ApiResponse<UserVO> detail(@Parameter(description = "用户 ID", required = true, example = "1") @PathVariable Long id) {
        return ApiResponse.ok(userService.getUser(id));
    }

    @SaCheckPermission("system:user:edit")
    @Operation(summary = "修改用户", description = "根据用户 ID 修改用户基础信息，不包含密码修改。")
    @PutMapping("/{id}")
    public ApiResponse<Void> update(@Parameter(description = "用户 ID", required = true, example = "1") @PathVariable Long id,
                                    @Valid @RequestBody UserUpdateRequest request) {
        userService.updateUser(id, request);
        return ApiResponse.ok();
    }

    @SaCheckPermission("system:user:delete")
    @Operation(summary = "删除用户", description = "逻辑删除用户。")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@Parameter(description = "用户 ID", required = true, example = "1") @PathVariable Long id) {
        userService.deleteUser(id);
        return ApiResponse.ok();
    }
}
