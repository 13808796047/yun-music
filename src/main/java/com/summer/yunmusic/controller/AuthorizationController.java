package com.summer.yunmusic.controller;

import com.summer.yunmusic.dto.AuthorizationDto;
import com.summer.yunmusic.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Summer
 * @Since 2022/4/20 12:48 AM
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/authorizations")
public class AuthorizationController {
    UserService userService;

    @PostMapping
    @ApiOperation("登录接口")
    public String store(@RequestBody AuthorizationDto authorizationDto) {

        return userService.store(authorizationDto);

    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
