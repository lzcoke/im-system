package org.yunqi.modules.serivce.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.yunqi.common.config.jwt.JwtUtils;
import org.yunqi.common.config.security.LoginUser;
import org.yunqi.common.utils.result.Result;
import org.yunqi.comon.utils.redis.RedisUtil;
import org.yunqi.modules.BO.LoginBO;
import org.yunqi.modules.serivce.AuthService;
import org.yunqi.modules.serivce.UserAccountService;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Result loginByPhoneAndPwd(LoginBO loginBO) {
        // AuthenticationManager 进行认证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginBO.getPhone(), loginBO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 认证未通过，给出提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }
        // 认证通过，使用userId生成jwt jwt存入返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId();
        String username = loginUser.getUsername();
        String token = jwtUtils.generateToken(username);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        // 完整用户信息存入redis
        redisUtil.set("LOGIN:APP:" + username, JSON.toJSONString(loginUser));
        return Result.ok(map);
    }

    @Override
    public Result getByToken(LoginBO login) {
        return Result.ok();
    }

    @Override
    public Result logOut() {
        // 获取SecurityContextHolder的username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth:{}", authentication);
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
        LoginUser loginUser = (LoginUser) authenticationToken.getPrincipal();
        String username = loginUser.getUser().getUsername();
        // 删除redis中的值
        redisUtil.del("LOGIN:APP:" + username);
        return Result.ok("退出成功");
    }
}
