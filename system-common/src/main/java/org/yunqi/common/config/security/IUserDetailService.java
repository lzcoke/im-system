package org.yunqi.common.config.security;

public interface IUserDetailService {
    SecurityUserInfo getByUserName(String username);
}
