package com.aiticket.server.system.menu.service;

import com.aiticket.server.system.menu.dto.MenuCreateRequest;
import com.aiticket.server.system.menu.dto.MenuQueryRequest;
import com.aiticket.server.system.menu.dto.MenuUpdateRequest;
import com.aiticket.server.system.menu.entity.SysMenu;
import com.aiticket.server.system.menu.vo.MenuVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {

    List<MenuVO> listMenus(MenuQueryRequest request);

    Long createMenu(MenuCreateRequest request);

    void updateMenu(Long id, MenuUpdateRequest request);

    MenuVO getMenu(Long id);

    void deleteMenu(Long id);
}
