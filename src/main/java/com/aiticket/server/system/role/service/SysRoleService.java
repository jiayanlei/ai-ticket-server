package com.aiticket.server.system.role.service;

import com.aiticket.server.common.page.PageResult;
import com.aiticket.server.system.role.dto.RoleCreateRequest;
import com.aiticket.server.system.role.dto.RoleQueryRequest;
import com.aiticket.server.system.role.dto.RoleUpdateRequest;
import com.aiticket.server.system.role.entity.SysRole;
import com.aiticket.server.system.role.vo.RoleVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysRoleService extends IService<SysRole> {

    PageResult<RoleVO> pageRoles(RoleQueryRequest request);

    Long createRole(RoleCreateRequest request);

    void updateRole(Long id, RoleUpdateRequest request);

    RoleVO getRole(Long id);

    void deleteRole(Long id);
}
