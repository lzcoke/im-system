package org.yunqi.modules.serivce.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.yunqi.common.config.security.IUserDetailService;
import org.yunqi.common.config.security.SecurityUserInfo;
import org.yunqi.modules.entity.User;
import org.yunqi.modules.entity.UserAccount;
import org.yunqi.modules.mapper.UserAccountMapper;
import org.yunqi.modules.mapper.UserMapper;
import org.yunqi.modules.serivce.UserService;

import javax.annotation.Resource;


@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService, IUserDetailService {
    @Resource
    private UserAccountMapper userAccountMapper;

    @Override
    public SecurityUserInfo getByUserName(String username) {
        UserAccount account = userAccountMapper.getByUserName(username, "PHONE");
        log.info("张哈：{}", account);
        User user = getById(account.getUserId());
        SecurityUserInfo securityUserInfo = new SecurityUserInfo();
        securityUserInfo.setUsername(account.getUsername());
        securityUserInfo.setPassword(account.getPassword());
        securityUserInfo.setId(user.getId());
        return securityUserInfo;
    }
}
