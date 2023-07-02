package org.yunqi.app.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.yunqi.common.config.jwt.UserUtils;
import org.yunqi.common.config.security.LoginUser;
import org.yunqi.common.config.security.SecurityUserInfo;
import org.yunqi.common.exception.ErrorEnum;
import org.yunqi.common.exception.ServiceException;
import org.yunqi.common.utils.PasswordUtil;
import org.yunqi.common.utils.RandomAccountUtil;
import org.yunqi.common.utils.SnowFlakeUtils;
import org.yunqi.common.utils.result.Result;
import org.yunqi.modules.entity.User;
import org.yunqi.modules.entity.UserAccount;
import org.yunqi.modules.serivce.UserAccountService;
import org.yunqi.modules.serivce.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//    @PreAuthorize("hasAuthority('test')")
    public Result detail() {
        SecurityUserInfo loginUser = UserUtils.getUser();
        String username = UserUtils.getUserName();
        String id = UserUtils.getUserId();
        List<String> roles = UserUtils.getRoles();
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("id", id);
        map.put("roles", roles);
        map.put("loginUser", loginUser);
        return Result.OK(map);
    }

    @PostMapping("/save")
    @ApiOperation(value = "新增用户")
//    @PreAuthorize("hasAuthority('admin')")
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
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userAccount.setPassword(passwordEncoder.encode("1234"));
        userAccountService.save(userAccount);
        return Result.OK();
    }
}
