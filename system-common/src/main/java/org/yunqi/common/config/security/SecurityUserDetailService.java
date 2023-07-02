package org.yunqi.common.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class SecurityUserDetailService implements UserDetailsService {
    @Autowired
    private IUserDetailService userDetailService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //todo 查询用户信息
        log.info("重写login方法:{}", username);
        SecurityUserInfo securityUserInfo = userDetailService.getByUserName(username);
        if (Objects.isNull(securityUserInfo)) {
            throw new RuntimeException("用户名或密码错误");
        }
        //todo 查询权限信息

        //todo 封装UserDetail对象

        return new LoginUser(securityUserInfo);
    }
}
