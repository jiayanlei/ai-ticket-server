package com.aiticket.server.system.menu.service.impl;

import com.aiticket.server.common.constant.CommonConstants;
import com.aiticket.server.common.exception.BusinessException;
import com.aiticket.server.common.exception.ErrorCode;
import com.aiticket.server.system.menu.convert.SysMenuConvert;
import com.aiticket.server.system.menu.dto.MenuCreateRequest;
import com.aiticket.server.system.menu.dto.MenuQueryRequest;
import com.aiticket.server.system.menu.dto.MenuUpdateRequest;
import com.aiticket.server.system.menu.entity.SysMenu;
import com.aiticket.server.system.menu.mapper.SysMenuMapper;
import com.aiticket.server.system.menu.service.SysMenuService;
import com.aiticket.server.system.menu.vo.MenuVO;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysMenuConvert menuConvert;

    @Override
    public List<MenuVO> listMenus(MenuQueryRequest request) {
        return list(Wrappers.<SysMenu>lambdaQuery()
                .like(StringUtils.hasText(request.getMenuName()), SysMenu::getMenuName, request.getMenuName())
                .eq(StringUtils.hasText(request.getMenuType()), SysMenu::getMenuType, request.getMenuType())
                .eq(StringUtils.hasText(request.getStatus()), SysMenu::getStatus, request.getStatus())
                .orderByAsc(SysMenu::getSortOrder)
                .orderByAsc(SysMenu::getId))
                .stream()
                .map(menuConvert::toVO)
                .toList();
    }

    @Override
    public Long createMenu(MenuCreateRequest request) {
        SysMenu menu = menuConvert.toEntity(request);
        if (menu.getParentId() == null) {
            menu.setParentId(0L);
        }
        if (menu.getVisible() == null) {
            menu.setVisible(true);
        }
        if (!StringUtils.hasText(menu.getStatus())) {
            menu.setStatus(CommonConstants.STATUS_ENABLED);
        }
        save(menu);
        return menu.getId();
    }

    @Override
    public void updateMenu(Long id, MenuUpdateRequest request) {
        SysMenu menu = getById(id);
        if (menu == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
        menuConvert.updateEntity(request, menu);
        updateById(menu);
    }

    @Override
    public MenuVO getMenu(Long id) {
        SysMenu menu = getById(id);
        if (menu == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
        return menuConvert.toVO(menu);
    }

    @Override
    public void deleteMenu(Long id) {
        if (!removeById(id)) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
    }
}
