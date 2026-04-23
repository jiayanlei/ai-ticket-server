package com.aiticket.server.auth.security;

import cn.dev33.satoken.stp.StpInterface;
import com.aiticket.server.system.menu.mapper.SysMenuMapper;
import com.aiticket.server.system.role.mapper.SysRoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SaPermissionService implements StpInterface {

    private static final long ADMIN_USER_ID = 1L;

    private final SysRoleMapper roleMapper;
    private final SysMenuMapper menuMapper;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        Long userId = Long.valueOf(String.valueOf(loginId));
        if (ADMIN_USER_ID == userId) {
            return List.of("*", "*:*:*");
        }
        return menuMapper.selectPermissionCodesByUserId(userId);
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        Long userId = Long.valueOf(String.valueOf(loginId));
        if (ADMIN_USER_ID == userId) {
            return List.of("admin");
        }
        return roleMapper.selectRoleCodesByUserId(userId);
    }
}
