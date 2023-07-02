package org.yunqi.comon.config.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.yunqi.comon.config.jwt.JwtUser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    //    @Autowired
//    private UserService userService;

    /**
     * 授权的时候是对角色授权，而认证的时候应该基于资源，而不是角色，因为资源是不变的，而用户的角色是会变的
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户账号获取用户信息
//        SysUser sysUser = userService.getUserByName(username);
//        if (null == sysUser) {
//            throw new UsernameNotFoundException(username);
//        }
        List<String> roles = new ArrayList<>();
        roles.add("super");
        return new JwtUser("18205718305", new BCryptPasswordEncoder().encode("123456"), roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }
}