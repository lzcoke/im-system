package org.yunqi.modules.auth.service.impl;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.yunqi.comon.config.jwt.JwtTokenUtil;
import org.yunqi.comon.config.jwt.JwtUser;
import org.yunqi.comon.config.jwt.LoginVO;
import org.yunqi.comon.utils.redis.RedisUtil;
import org.yunqi.comon.utils.result.Result;
import org.yunqi.modules.auth.BO.LoginBO;
import org.yunqi.modules.auth.service.AuthService;

import java.util.Map;
import java.util.Objects;

import static org.yunqi.comon.config.jwt.JwtConstant.TOKEN_PREFIX;
import static org.yunqi.comon.utils.constant.Const.MAX_USER_EXIST_TIME;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Result loginByPhoneAndPwd(LoginBO loginBO) {
        /*前往security 认证*/
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(loginBO.getPhone(), loginBO.getPassword());
        /*调用JwtUserDetailService下的loginUserByUsername方法*/
        Authentication authentication = authenticationManager.authenticate(upToken);
        if (Objects.isNull(authentication)) {
            throw new RuntimeException("认证失败");
        }
//        JwtUser user = JSONUtil(JSONUtil.toJsonStr(authentication.getPrincipal()), JwtUser.class);
        JwtUser user = JSON.parseObject(JSON.toJSONString(authentication.getPrincipal()), JwtUser.class);
        log.info("user:{}", user);
        String token = jwtTokenUtil.generateToken(user);
//        JwtUser userDetails = (JwtUser) authentication;
//        log.info("userDetails:{}",authentication.getPrincipal());
//        UserDetails userDetails = userDetailsService.loadUserByUsername(loginBO.getPhone());
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(TOKEN_PREFIX + token);
        // 保存入redis
        redisUtil.set(loginVO.getToken(), JSON.toJSONString(loginVO), MAX_USER_EXIST_TIME);
        return Result.ok(loginVO);
    }
}
