package com.aiticket.server.system.user.service;

import com.aiticket.server.common.page.PageResult;
import com.aiticket.server.system.user.dto.UserCreateRequest;
import com.aiticket.server.system.user.dto.UserQueryRequest;
import com.aiticket.server.system.user.dto.UserUpdateRequest;
import com.aiticket.server.system.user.entity.SysUser;
import com.aiticket.server.system.user.vo.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysUserService extends IService<SysUser> {

    PageResult<UserVO> pageUsers(UserQueryRequest request);

    Long createUser(UserCreateRequest request);

    void updateUser(Long id, UserUpdateRequest request);

    UserVO getUser(Long id);

    void deleteUser(Long id);
}
