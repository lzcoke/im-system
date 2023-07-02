package org.yunqi.common.config.security;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class LoginUser implements UserDetails {
    // 用户信息
    private SecurityUserInfo user;
    // 权限
    private List<String> permissions;

    /**
     * 存储redis不进入序列化
     */
    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;

    public LoginUser(SecurityUserInfo user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    /**
     * 权限信息
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null) {
            return authorities;
        }
        // permissions string权限信息封装
        List<SimpleGrantedAuthority> collect =
                permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * 是否过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 是否过期
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
