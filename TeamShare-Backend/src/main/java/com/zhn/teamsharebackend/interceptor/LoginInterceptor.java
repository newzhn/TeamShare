package com.zhn.teamsharebackend.interceptor;

import com.google.gson.Gson;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.exception.ErrorCode;
import com.zhn.teamsharebackend.utils.UserHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 * @author zhn
 * @version 1.0
 */
public class LoginInterceptor implements HandlerInterceptor {
    private static final String METHOD_GET = "GET";
    private static final String METHOD_OPTIONS = "OPTIONS";
    private static final String URI_ADMIN = "/api/admin/";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //对所有非Get请求和符合后台管理系统URL请求进行拦截登录验证
        String method = request.getMethod();
        String uri = request.getRequestURI();
        //options请求是预检请求，统一放行
        if (METHOD_OPTIONS.equalsIgnoreCase(method)) {
            return true;
        }
        //首先判断是否是管理后台的请求路径获取是否是除Get、options之外的请求，是则验证
        if (uri.startsWith(URI_ADMIN) || !METHOD_GET.equalsIgnoreCase(method)) {
            if (UserHolder.getUser() == null) {
                Result<Object> result = Result.fail(ErrorCode.NOT_LOGIN);
                String json = new Gson().toJson(result);
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(json);
                return false;
            }
        }
        return true;
    }
}
