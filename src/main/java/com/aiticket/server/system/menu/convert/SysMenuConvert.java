package com.aiticket.server.system.menu.convert;

import com.aiticket.server.system.menu.dto.MenuCreateRequest;
import com.aiticket.server.system.menu.dto.MenuUpdateRequest;
import com.aiticket.server.system.menu.entity.SysMenu;
import com.aiticket.server.system.menu.vo.MenuVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SysMenuConvert {

    SysMenu toEntity(MenuCreateRequest request);

    void updateEntity(MenuUpdateRequest request, @MappingTarget SysMenu entity);

    MenuVO toVO(SysMenu entity);
}
