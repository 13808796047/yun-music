package com.summer.yunmusic.controller;

import com.summer.yunmusic.dto.AuthorizationDto;
import com.summer.yunmusic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String store(AuthorizationDto authorizationDto) {

        return userService.store(authorizationDto);

    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
