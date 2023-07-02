package org.yunqi.common.config.jwt;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import org.yunqi.common.config.security.LoginUser;
import org.yunqi.common.exception.ErrorEnum;
import org.yunqi.common.exception.ServiceException;
import org.yunqi.comon.utils.redis.RedisUtil;

/**
 * jwt过滤器
 */
@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            // 放行
            filterChain.doFilter(request, response);
            return;
        }
        // 解析token
        String username;
        try {
            Claims claims = jwtUtils.pareJWT(token);
            username = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(ErrorEnum.UNAUTHORIZED);
        }
        // redis中获取用户信息
        Object redisString = redisUtil.get("LOGIN:APP:" + username);
        if (Objects.isNull(redisString)) {
            throw new ServiceException(ErrorEnum.USER_IS_NULL);
        }
        LoginUser loginUser = JSON.parseObject(redisString.toString(), LoginUser.class);
        // 存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,
                null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }

}
