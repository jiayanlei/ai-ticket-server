package com.aiticket.server.system.role.service.impl;

import com.aiticket.server.common.constant.CommonConstants;
import com.aiticket.server.common.exception.BusinessException;
import com.aiticket.server.common.exception.ErrorCode;
import com.aiticket.server.common.page.PageResult;
import com.aiticket.server.system.role.convert.SysRoleConvert;
import com.aiticket.server.system.role.dto.RoleCreateRequest;
import com.aiticket.server.system.role.dto.RoleQueryRequest;
import com.aiticket.server.system.role.dto.RoleUpdateRequest;
import com.aiticket.server.system.role.entity.SysRole;
import com.aiticket.server.system.role.mapper.SysRoleMapper;
import com.aiticket.server.system.role.service.SysRoleService;
import com.aiticket.server.system.role.vo.RoleVO;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleConvert roleConvert;

    @Override
    public PageResult<RoleVO> pageRoles(RoleQueryRequest request) {
        Page<SysRole> page = page(new Page<>(request.getPageNum(), request.getPageSize()),
                Wrappers.<SysRole>lambdaQuery()
                        .like(StringUtils.hasText(request.getRoleName()), SysRole::getRoleName, request.getRoleName())
                        .like(StringUtils.hasText(request.getRoleCode()), SysRole::getRoleCode, request.getRoleCode())
                        .eq(StringUtils.hasText(request.getStatus()), SysRole::getStatus, request.getStatus())
                        .orderByAsc(SysRole::getSortOrder)
                        .orderByDesc(SysRole::getCreateTime));
        List<RoleVO> records = page.getRecords().stream().map(roleConvert::toVO).toList();
        return PageResult.of(page, records);
    }

    @Override
    public Long createRole(RoleCreateRequest request) {
        long exists = count(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getRoleCode, request.getRoleCode()));
        if (exists > 0) {
            throw new BusinessException("角色编码已存在");
        }
        SysRole role = roleConvert.toEntity(request);
        if (!StringUtils.hasText(role.getStatus())) {
            role.setStatus(CommonConstants.STATUS_ENABLED);
        }
        save(role);
        return role.getId();
    }

    @Override
    public void updateRole(Long id, RoleUpdateRequest request) {
        SysRole role = getById(id);
        if (role == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
        roleConvert.updateEntity(request, role);
        updateById(role);
    }

    @Override
    public RoleVO getRole(Long id) {
        SysRole role = getById(id);
        if (role == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
        return roleConvert.toVO(role);
    }

    @Override
    public void deleteRole(Long id) {
        if (!removeById(id)) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
    }
}
