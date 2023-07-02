package org.yunqi.app.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yunqi.comon.utils.result.Result;

import static org.yunqi.comon.utils.constant.RequestConstant.APP_URL;

@RestController
@RequestMapping(APP_URL + "/user")
public class UserController {
    @GetMapping("/detail")
    @ApiOperation(value = "用户信息")
    public Result detail() {
        return Result.OK();
    }
}
