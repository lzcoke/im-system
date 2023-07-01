package org.yunqi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Api(tags = "测试服务")
public class TestController {

    @GetMapping("/hello")
    @ApiOperation(value = "测试接口")
    public String helloWord() {
        return "123";
    }
}
