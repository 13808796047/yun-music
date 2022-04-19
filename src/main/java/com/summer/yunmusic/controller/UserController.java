package com.summer.yunmusic.controller;

import com.summer.yunmusic.dto.UserCreateDto;
import com.summer.yunmusic.dto.UserUpdateDto;
import com.summer.yunmusic.mapper.UserMapper;
import com.summer.yunmusic.service.UserService;
import com.summer.yunmusic.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Summer
 * @since 2022/4/17 14:35
 */
@RestController
@RequestMapping("/users")
public class UserController {


    UserService userService;
    UserMapper userMapper;

    @PostMapping
    UserVo create(@Validated @RequestBody UserCreateDto userCreateDto) {
        return userMapper.toVo(userService.create(userCreateDto));
    }

    /**
     * 列表
     *
     * @param pageable
     * @return
     */
    @GetMapping
    Page<UserVo> search(@PageableDefault(sort = {"createdTime"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return userService.search(pageable)
                .map(userMapper::toVo);

    }

    @GetMapping("/{id}")
    UserVo get(@PathVariable String id) {
        return userMapper.toVo(userService.get(id));
    }

    @PutMapping("/{id}")
    UserVo update(@PathVariable String id,
                  @Validated @RequestBody UserUpdateDto userUpdateDto) {
        return userMapper.toVo(userService.update(id, userUpdateDto));
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable String id) {
        userService.delete(id);
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
