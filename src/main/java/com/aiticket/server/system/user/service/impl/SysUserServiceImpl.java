package com.aiticket.server.system.user.service.impl;

import com.aiticket.server.common.constant.CommonConstants;
import com.aiticket.server.common.exception.BusinessException;
import com.aiticket.server.common.exception.ErrorCode;
import com.aiticket.server.common.page.PageResult;
import com.aiticket.server.system.user.convert.SysUserConvert;
import com.aiticket.server.system.user.dto.UserCreateRequest;
import com.aiticket.server.system.user.dto.UserQueryRequest;
import com.aiticket.server.system.user.dto.UserUpdateRequest;
import com.aiticket.server.system.user.entity.SysUser;
import com.aiticket.server.system.user.mapper.SysUserMapper;
import com.aiticket.server.system.user.service.SysUserService;
import com.aiticket.server.system.user.vo.UserVO;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserConvert userConvert;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public PageResult<UserVO> pageUsers(UserQueryRequest request) {
        Page<SysUser> page = page(new Page<>(request.getPageNum(), request.getPageSize()),
                Wrappers.<SysUser>lambdaQuery()
                        .like(StringUtils.hasText(request.getUsername()), SysUser::getUsername, request.getUsername())
                        .like(StringUtils.hasText(request.getNickname()), SysUser::getNickname, request.getNickname())
                        .eq(request.getDeptId() != null, SysUser::getDeptId, request.getDeptId())
                        .eq(StringUtils.hasText(request.getStatus()), SysUser::getStatus, request.getStatus())
                        .orderByDesc(SysUser::getCreateTime));
        List<UserVO> records = page.getRecords().stream().map(userConvert::toVO).toList();
        return PageResult.of(page, records);
    }

    @Override
    public Long createUser(UserCreateRequest request) {
        long exists = count(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, request.getUsername()));
        if (exists > 0) {
            throw new BusinessException("用户名已存在");
        }
        SysUser user = userConvert.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        if (!StringUtils.hasText(user.getStatus())) {
            user.setStatus(CommonConstants.STATUS_ENABLED);
        }
        save(user);
        return user.getId();
    }

    @Override
    public void updateUser(Long id, UserUpdateRequest request) {
        SysUser user = getById(id);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
        userConvert.updateEntity(request, user);
        updateById(user);
    }

    @Override
    public UserVO getUser(Long id) {
        SysUser user = getById(id);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
        return userConvert.toVO(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!removeById(id)) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
    }
}
