package com.zhn.teamsharebackend.interceptor;

import com.zhn.teamsharebackend.utils.UserHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhn
 * @version 1.0
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getRequestURI().startsWith("/api/admin/")) {
            // 对以 /api/admin/ 开头的请求进行身份验证
            // 如果身份验证失败，则抛出异常或重定向到登录页面
            //判断ThreadLocal中是否有用户信息
            if (UserHolder.getUser() == null) {
                return false;
            }
        }
        return true;
    }
}
