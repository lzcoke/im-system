package org.yunqi.modules.auth.BO;

import lombok.Data;

@Data
public class LoginBO {
    /*手机号*/
    private String phone;
    /*密码*/
    private String password;
}
