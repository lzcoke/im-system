package org.yunqi.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yunqi.comon.utils.result.Result;
import org.yunqi.modules.auth.BO.LoginBO;
import org.yunqi.modules.auth.service.AuthService;

import static org.yunqi.comon.utils.constant.RequestConstant.APP_URL;

@RestController
@RequestMapping(APP_URL + "/auth")
@Api(tags = "app登录信息")
@Slf4j
public class LoginController {
    @Autowired
    private AuthService authService;

    @PostMapping("/loginByPhoneAndPwd")
    @ApiOperation("登录")
    public Result loginByPhoneAndPwd(@RequestBody LoginBO login) {
        log.info("login:{}",login);
        return authService.loginByPhoneAndPwd(login);
    }
}
