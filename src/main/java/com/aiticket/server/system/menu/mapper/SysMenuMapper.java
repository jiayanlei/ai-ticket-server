package com.aiticket.server.system.menu.mapper;

import com.aiticket.server.system.menu.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu> {

    @Select("""
            <script>
            select distinct perms
            from sys_menu
            where deleted = 0
              and status = 'ENABLED'
              and perms is not null
              and perms != ''
            <if test="userId != 1">
              and 1 = 0
            </if>
            </script>
            """)
    List<String> selectPermissionCodesByUserId(@Param("userId") Long userId);
}
