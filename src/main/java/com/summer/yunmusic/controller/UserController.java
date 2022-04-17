package com.summer.yunmusic.controller;

import com.summer.yunmusic.dto.UserCreateDto;
import com.summer.yunmusic.mapper.UserMapper;
import com.summer.yunmusic.service.UserService;
import com.summer.yunmusic.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Summer
 * @since 2022/4/17 14:35
 */
@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;
    UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    UserVo create(@RequestBody UserCreateDto userCreateDto) {
        return userMapper.toVo(userService.create(userCreateDto));
    }

    @GetMapping
    List<UserVo> list() {
        return userService.list()
                .stream()
                .map(userMapper::toVo)
                .collect(Collectors.toList());
    }
}
