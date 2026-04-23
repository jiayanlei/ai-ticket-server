package com.aiticket.server.system.dept.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.aiticket.server.common.core.ApiResponse;
import com.aiticket.server.config.OpenApiConfig;
import com.aiticket.server.system.dept.dto.DeptCreateRequest;
import com.aiticket.server.system.dept.dto.DeptQueryRequest;
import com.aiticket.server.system.dept.dto.DeptUpdateRequest;
import com.aiticket.server.system.dept.service.SysDeptService;
import com.aiticket.server.system.dept.vo.DeptVO;
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

@Tag(name = "部门管理")
@SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/depts")
public class SysDeptController {

    private final SysDeptService deptService;

    @SaCheckPermission("system:dept:list")
    @Operation(summary = "部门列表", description = "按部门名称、编码、状态筛选部门列表。当前返回平铺列表，前端如需树结构需按 parentId 组装。")
    @GetMapping
    public ApiResponse<List<DeptVO>> list(@ParameterObject DeptQueryRequest request) {
        return ApiResponse.ok(deptService.listDepts(request));
    }

    @SaCheckPermission("system:dept:add")
    @Operation(summary = "新增部门", description = "创建部门并返回新部门 ID。deptCode 必须唯一，parentId 为空时服务端默认为 0。")
    @PostMapping
    public ApiResponse<Long> create(@Valid @RequestBody DeptCreateRequest request) {
        return ApiResponse.ok(deptService.createDept(request));
    }

    @SaCheckPermission("system:dept:query")
    @Operation(summary = "部门详情", description = "根据部门 ID 查询部门详情。")
    @GetMapping("/{id}")
    public ApiResponse<DeptVO> detail(@Parameter(description = "部门 ID", required = true, example = "1") @PathVariable Long id) {
        return ApiResponse.ok(deptService.getDept(id));
    }

    @SaCheckPermission("system:dept:edit")
    @Operation(summary = "修改部门", description = "根据部门 ID 修改部门基础信息。")
    @PutMapping("/{id}")
    public ApiResponse<Void> update(@Parameter(description = "部门 ID", required = true, example = "1") @PathVariable Long id,
                                    @Valid @RequestBody DeptUpdateRequest request) {
        deptService.updateDept(id, request);
        return ApiResponse.ok();
    }

    @SaCheckPermission("system:dept:delete")
    @Operation(summary = "删除部门", description = "逻辑删除部门。")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@Parameter(description = "部门 ID", required = true, example = "1") @PathVariable Long id) {
        deptService.deleteDept(id);
        return ApiResponse.ok();
    }
}
