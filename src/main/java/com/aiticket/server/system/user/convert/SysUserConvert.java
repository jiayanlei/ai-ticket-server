package com.aiticket.server.system.user.convert;

import com.aiticket.server.system.user.dto.UserCreateRequest;
import com.aiticket.server.system.user.dto.UserUpdateRequest;
import com.aiticket.server.system.user.entity.SysUser;
import com.aiticket.server.system.user.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SysUserConvert {

    SysUser toEntity(UserCreateRequest request);

    void updateEntity(UserUpdateRequest request, @MappingTarget SysUser entity);

    UserVO toVO(SysUser entity);
}
