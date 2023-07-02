package org.yunqi.modules.mapper;

import org.apache.ibatis.annotations.Param;
import org.yunqi.common.config.mybatis.BaseMapper;
import org.yunqi.modules.entity.UserAccount;

public interface UserAccountMapper extends BaseMapper<UserAccount> {
    UserAccount getByUserName(@Param("username") String username, @Param("source") String source);
}
