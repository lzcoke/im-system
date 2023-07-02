package org.yunqi.modules.serivce;

import org.yunqi.common.utils.result.Result;
import org.yunqi.modules.BO.LoginBO;

public interface AuthService {
    Result loginByPhoneAndPwd(LoginBO loginBO);

    Result getByToken(LoginBO login);

    Result logOut();
}
