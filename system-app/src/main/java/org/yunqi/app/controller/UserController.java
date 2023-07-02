package org.yunqi.app.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yunqi.common.utils.PasswordUtil;
import org.yunqi.common.utils.RandomAccountUtil;
import org.yunqi.common.utils.SnowFlakeUtils;
import org.yunqi.common.utils.result.Result;
import org.yunqi.modules.entity.User;
import org.yunqi.modules.entity.UserAccount;
import org.yunqi.modules.serivce.UserAccountService;
import org.yunqi.modules.serivce.UserService;

import static org.yunqi.common.utils.constant.RequestConstant.APP_URL;

@RestController
@RequestMapping(APP_URL + "/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserAccountService userAccountService;

    @GetMapping("/detail")
    @ApiOperation(value = "用户信息")
    public Result detail() {
        return Result.OK();
    }

    @PostMapping("/save")
    @ApiOperation(value = "新增用户")
    public Result add(@RequestBody User user) {
        long nextId = SnowFlakeUtils.nextId();
        user.setUid(String.valueOf(nextId));
        userService.save(user);
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(user.getId());
        userAccount.setUsername(user.getPhone());
        userAccount.setSource("PHONE");
        String salt = RandomAccountUtil.randomGen(8);
        userAccount.setSalt(salt);
        userAccount.setPassword(PasswordUtil.encrypt(user.getPhone(), "1234567", salt));
        userAccountService.save(userAccount);
        return Result.OK();
    }
}
