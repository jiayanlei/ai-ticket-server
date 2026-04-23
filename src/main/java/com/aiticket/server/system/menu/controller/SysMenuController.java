package com.aiticket.server.system.menu.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.aiticket.server.common.core.ApiResponse;
import com.aiticket.server.config.OpenApiConfig;
import com.aiticket.server.system.menu.dto.MenuCreateRequest;
import com.aiticket.server.system.menu.dto.MenuQueryRequest;
import com.aiticket.server.system.menu.dto.MenuUpdateRequest;
import com.aiticket.server.system.menu.service.SysMenuService;
import com.aiticket.server.system.menu.vo.MenuVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "菜单管理")
@SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/menus")
public class SysMenuController {

    private final SysMenuService menuService;

    @SaCheckPermission("system:menu:list")
    @Operation(summary = "菜单列表", description = "按菜单名称、类型、状态筛选菜单列表。返回平铺列表，前端可按 parentId 组装菜单树、路由和按钮权限。")
    @GetMapping
    public ApiResponse<List<MenuVO>> list(@ParameterObject MenuQueryRequest request) {
        return ApiResponse.ok(menuService.listMenus(request));
    }

    @SaCheckPermission("system:menu:add")
    @Operation(summary = "新增菜单", description = "创建目录、菜单或按钮权限并返回新菜单 ID。parentId 为空时服务端默认为 0，visible 为空时默认为 true。")
    @PostMapping
    public ApiResponse<Long> create(@Valid @RequestBody MenuCreateRequest request) {
        return ApiResponse.ok(menuService.createMenu(request));
    }

    @SaCheckPermission("system:menu:query")
    @Operation(summary = "菜单详情", description = "根据菜单 ID 查询菜单详情。")
    @GetMapping("/{id}")
    public ApiResponse<MenuVO> detail(@Parameter(description = "菜单 ID", required = true, example = "110") @PathVariable Long id) {
        return ApiResponse.ok(menuService.getMenu(id));
    }

    @SaCheckPermission("system:menu:edit")
    @Operation(summary = "修改菜单", description = "根据菜单 ID 修改菜单、路由或按钮权限信息。")
    @PutMapping("/{id}")
    public ApiResponse<Void> update(@Parameter(description = "菜单 ID", required = true, example = "110") @PathVariable Long id,
                                    @Valid @RequestBody MenuUpdateRequest request) {
        menuService.updateMenu(id, request);
        return ApiResponse.ok();
    }

    @SaCheckPermission("system:menu:delete")
    @Operation(summary = "删除菜单", description = "逻辑删除菜单或按钮权限。")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@Parameter(description = "菜单 ID", required = true, example = "110") @PathVariable Long id) {
        menuService.deleteMenu(id);
        return ApiResponse.ok();
    }
}
