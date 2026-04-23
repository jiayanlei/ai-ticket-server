package com.aiticket.server.auth.service;

import com.aiticket.server.auth.dto.LoginRequest;
import com.aiticket.server.auth.vo.LoginVO;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    LoginVO login(LoginRequest request, HttpServletRequest servletRequest);

    void logout();

    LoginVO currentUser();
}
