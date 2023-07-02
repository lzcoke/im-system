package org.yunqi.modules.auth.service;

import org.yunqi.comon.utils.result.Result;
import org.yunqi.modules.auth.BO.LoginBO;

public interface AuthService {
    Result loginByPhoneAndPwd(LoginBO loginBO);
}
