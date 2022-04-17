package com.summer.yunmusic.service.impl;

import com.summer.yunmusic.dto.UserDto;
import com.summer.yunmusic.mapper.UserMapper;
import com.summer.yunmusic.repository.UserRepository;
import com.summer.yunmusic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Summer
 * @since 2022/4/17 14:57
 */
@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    UserMapper userMapper;

    /**
     * 依赖注入方式
     *
     * @param userMapper
     */
    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> list() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
}
