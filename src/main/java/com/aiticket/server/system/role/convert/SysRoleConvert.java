package com.aiticket.server.system.role.convert;

import com.aiticket.server.system.role.dto.RoleCreateRequest;
import com.aiticket.server.system.role.dto.RoleUpdateRequest;
import com.aiticket.server.system.role.entity.SysRole;
import com.aiticket.server.system.role.vo.RoleVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SysRoleConvert {

    SysRole toEntity(RoleCreateRequest request);

    void updateEntity(RoleUpdateRequest request, @MappingTarget SysRole entity);

    RoleVO toVO(SysRole entity);
}
