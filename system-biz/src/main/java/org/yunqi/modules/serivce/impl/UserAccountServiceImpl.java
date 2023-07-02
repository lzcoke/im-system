package org.yunqi.modules.serivce.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.yunqi.modules.entity.UserAccount;
import org.yunqi.modules.mapper.UserAccountMapper;
import org.yunqi.modules.serivce.UserAccountService;


@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService {

}
