package com.mana.sys.component;

import com.mana.sys.constants.SysConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author: chenzhiyan
 * @createDate: 2024/06/30
 * @description: Login verification for requests, no realization because of no requirements
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    /*
     * Login verification
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String headerAccount = request.getHeader(SysConstants.HEADER_ACCOUNT);
        return true;
    }
}