package org.yunqi.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.yunqi.common.utils.result.Result;
import org.yunqi.modules.BO.LoginBO;
import org.yunqi.modules.serivce.AuthService;

import static org.yunqi.common.utils.constant.RequestConstant.APP_URL;

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
        log.info("login:{}", login);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(login.getPassword());
        log.info("encode:{}", encode);
        return authService.loginByPhoneAndPwd(login);
    }

    @PostMapping("/getByToken")
    @ApiOperation("根据token获取用户信息")
    public Result getByToken(@RequestBody LoginBO login) {
        return authService.getByToken(login);
    }

    @GetMapping("/logOut")
    @ApiOperation("退出")
    public Result logOut() {
        return authService.logOut();
    }
}
