package com.summer.yunmusic.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.summer.yunmusic.config.SecurityConfig;
import com.summer.yunmusic.dto.AuthorizationDto;
import com.summer.yunmusic.dto.UserCreateDto;
import com.summer.yunmusic.dto.UserDto;
import com.summer.yunmusic.dto.UserUpdateDto;
import com.summer.yunmusic.entity.User;
import com.summer.yunmusic.exception.BizException;
import com.summer.yunmusic.exception.ExceptionEnum;
import com.summer.yunmusic.mapper.UserMapper;
import com.summer.yunmusic.repository.UserRepository;
import com.summer.yunmusic.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * @author Summer
 * @since 2022/4/17 14:57
 */
@Service
@Slf4j
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
        if (!user.isPresent()) {
            throw new BizException(ExceptionEnum.USER_NOT_FOUND);
        }
//        log.info("查到数据{}",user.get());
        return user.get();
    }

    @Override
    public UserDto get(String id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new BizException(ExceptionEnum.USER_NOT_FOUND);
        }
        return userMapper.toDto(user.get());
    }

    @Override
    public UserDto update(String id, UserUpdateDto userUpdateDto) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new BizException(ExceptionEnum.USER_NOT_FOUND);
        }
        return userMapper.toDto(userRepository.save(userMapper.updateEntity(user.get(), userUpdateDto)));
    }

    @Override
    public void delete(String id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new BizException(ExceptionEnum.USER_NOT_FOUND);
        }
        userRepository.delete(user.get());
    }

    @Override
    public Page<UserDto> search(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }

    @Override
    public String store(AuthorizationDto authorizationDto) {
        User user = loadUserByUsername(authorizationDto.getUsername());
        if (!passwordEncoder.matches(authorizationDto.getPassword(), user.getPassword())) {
            throw new BizException(ExceptionEnum.USER_PASSWORD_NOT_MATCH);
        }
        if (!user.isEnabled()) {
            throw new BizException(ExceptionEnum.USER_NOT_ENABLED);
        }
//        log.info("user.getLocked(){}", user.isAccountNonLocked());
        if (user.isAccountNonLocked()) {
            throw new BizException(ExceptionEnum.USER_LOCKED);
        }

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConfig.SECRET.getBytes()));
    }

    @Override
    public UserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = loadUserByUsername(authentication.getName());

        return userMapper.toDto(user);
    }

    private void checkUserName(String username) {

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            throw new BizException(ExceptionEnum.USER_NAME_DUPLICATE);
        }
    }
}
