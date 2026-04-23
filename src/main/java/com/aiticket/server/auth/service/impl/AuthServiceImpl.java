package com.aiticket.server.auth.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.aiticket.server.auth.dto.LoginRequest;
import com.aiticket.server.auth.service.AuthService;
import com.aiticket.server.auth.vo.LoginVO;
import com.aiticket.server.common.constant.CommonConstants;
import com.aiticket.server.common.exception.BusinessException;
import com.aiticket.server.common.exception.ErrorCode;
import com.aiticket.server.system.loginlog.entity.SysLoginLog;
import com.aiticket.server.system.loginlog.mapper.SysLoginLogMapper;
import com.aiticket.server.system.user.entity.SysUser;
import com.aiticket.server.system.user.mapper.SysUserMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SysUserMapper userMapper;
    private final SysLoginLogMapper loginLogMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public LoginVO login(LoginRequest request, HttpServletRequest servletRequest) {
        SysUser user = userMapper.selectOne(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUsername, request.getUsername()));
        if (user == null) {
            recordLoginLog(request.getUsername(), servletRequest, "FAIL", "用户不存在");
            throw new BusinessException(ErrorCode.UNAUTHORIZED.getCode(), "用户名或密码错误");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            recordLoginLog(request.getUsername(), servletRequest, "FAIL", "密码错误");
            throw new BusinessException(ErrorCode.UNAUTHORIZED.getCode(), "用户名或密码错误");
        }
        if (!CommonConstants.STATUS_ENABLED.equals(user.getStatus())) {
            recordLoginLog(request.getUsername(), servletRequest, "FAIL", "用户已停用");
            throw new BusinessException(ErrorCode.FORBIDDEN.getCode(), "用户已停用");
        }

        StpUtil.login(user.getId());
        StpUtil.getSession().set("userId", user.getId());
        StpUtil.getSession().set("username", user.getUsername());
        StpUtil.getSession().set("nickname", user.getNickname());

        user.setLastLoginTime(LocalDateTime.now());
        userMapper.updateById(user);
        recordLoginLog(request.getUsername(), servletRequest, "SUCCESS", "登录成功");

        return buildLoginVO(user);
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }

    @Override
    public LoginVO currentUser() {
        Long userId = StpUtil.getLoginIdAsLong();
        SysUser user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED);
        }
        return buildLoginVO(user);
    }

    private LoginVO buildLoginVO(SysUser user) {
        return LoginVO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .tokenName(StpUtil.getTokenName())
                .tokenValue(StpUtil.getTokenValue())
                .tokenPrefix("Bearer")
                .roles(StpUtil.getRoleList())
                .permissions(StpUtil.getPermissionList())
                .build();
    }

    private void recordLoginLog(String username, HttpServletRequest request, String status, String message) {
        try {
            SysLoginLog logRecord = new SysLoginLog();
            logRecord.setUsername(username);
            logRecord.setLoginIp(resolveClientIp(request));
            logRecord.setUserAgent(request.getHeader("User-Agent"));
            logRecord.setStatus(status);
            logRecord.setMessage(message);
            logRecord.setLoginTime(LocalDateTime.now());
            loginLogMapper.insert(logRecord);
        } catch (Exception ex) {
            log.warn("Record login log failed: {}", ex.getMessage());
        }
    }

    private String resolveClientIp(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (StringUtils.hasText(forwardedFor)) {
            return forwardedFor.split(",")[0].trim();
        }
        String realIp = request.getHeader("X-Real-IP");
        if (StringUtils.hasText(realIp)) {
            return realIp;
        }
        return request.getRemoteAddr();
    }
}
