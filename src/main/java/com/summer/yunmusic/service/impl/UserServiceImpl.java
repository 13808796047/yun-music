package com.summer.yunmusic.service.impl;

import com.summer.yunmusic.dto.UserCreateDto;
import com.summer.yunmusic.dto.UserDto;
import com.summer.yunmusic.entity.User;
import com.summer.yunmusic.exception.BizException;
import com.summer.yunmusic.exception.ExceptionEnum;
import com.summer.yunmusic.mapper.UserMapper;
import com.summer.yunmusic.repository.UserRepository;
import com.summer.yunmusic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Summer
 * @since 2022/4/17 14:57
 */
@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

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

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDto> list() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto create(UserCreateDto userCreateDto) {
        // 检查用户名存在
        checkUserName(userCreateDto.getUsername());
        User user = userMapper.createEntity(userCreateDto);
        // 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            throw new BizException(ExceptionEnum.USER_NOT_FOUND);
        }
        return user.get();
    }

    private void checkUserName(String username) {

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            throw new BizException(ExceptionEnum.USER_NAMEDUPLICATE);
        }
    }
}
