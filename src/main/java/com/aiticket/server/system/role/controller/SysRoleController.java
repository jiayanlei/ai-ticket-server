package com.aiticket.server.system.role.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.aiticket.server.common.core.ApiResponse;
import com.aiticket.server.common.page.PageResult;
import com.aiticket.server.config.OpenApiConfig;
import com.aiticket.server.system.role.dto.RoleCreateRequest;
import com.aiticket.server.system.role.dto.RoleQueryRequest;
import com.aiticket.server.system.role.dto.RoleUpdateRequest;
import com.aiticket.server.system.role.service.SysRoleService;
import com.aiticket.server.system.role.vo.RoleVO;
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
@Tag(name = "角色管理")
@SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/roles")
public class SysRoleController {

    private final SysRoleService roleService;

    @SaCheckPermission("system:role:list")
    @Operation(summary = "角色分页列表", description = "按角色名称、编码、状态筛选角色，返回统一分页结构。")
    @GetMapping
    public ApiResponse<PageResult<RoleVO>> list(@ParameterObject @Valid RoleQueryRequest request) {
        return ApiResponse.ok(roleService.pageRoles(request));
    }

    @SaCheckPermission("system:role:add")
    @Operation(summary = "新增角色", description = "创建角色并返回新角色 ID。roleCode 必须唯一。")
    @PostMapping
    public ApiResponse<Long> create(@Valid @RequestBody RoleCreateRequest request) {
        return ApiResponse.ok(roleService.createRole(request));
    }

    @SaCheckPermission("system:role:query")
    @Operation(summary = "角色详情", description = "根据角色 ID 查询角色详情。")
    @GetMapping("/{id}")
    public ApiResponse<RoleVO> detail(@Parameter(description = "角色 ID", required = true, example = "1") @PathVariable Long id) {
        return ApiResponse.ok(roleService.getRole(id));
    }

    @SaCheckPermission("system:role:edit")
    @Operation(summary = "修改角色", description = "根据角色 ID 修改角色基础信息。")
    @PutMapping("/{id}")
    public ApiResponse<Void> update(@Parameter(description = "角色 ID", required = true, example = "1") @PathVariable Long id,
                                    @Valid @RequestBody RoleUpdateRequest request) {
        roleService.updateRole(id, request);
        return ApiResponse.ok();
    }

    @SaCheckPermission("system:role:delete")
    @Operation(summary = "删除角色", description = "逻辑删除角色。")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@Parameter(description = "角色 ID", required = true, example = "1") @PathVariable Long id) {
        roleService.deleteRole(id);
        return ApiResponse.ok();
    }
}
