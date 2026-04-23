package com.aiticket.server.system.role.mapper;

import com.aiticket.server.system.role.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Select("""
            select distinct r.role_code
            from sys_role r
            inner join sys_user_role ur on ur.role_id = r.id
            where ur.user_id = #{userId}
              and r.deleted = 0
              and r.status = 'ENABLED'
            """)
    List<String> selectRoleCodesByUserId(@Param("userId") Long userId);
}
