package org.yunqi.common.config.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.yunqi.common.utils.result.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhengliu
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        // 处理异常
        if (authException instanceof InsufficientAuthenticationException) {
            ResponseUtil.out(response, Result.error(401, "请先登录~~"));
        } else if (authException instanceof BadCredentialsException) {
            ResponseUtil.out(response, Result.error(401, "用户名或密码错误"));
        } else {
            ResponseUtil.out(response, Result.error(401, "认证失败"));
        }
    }
}
