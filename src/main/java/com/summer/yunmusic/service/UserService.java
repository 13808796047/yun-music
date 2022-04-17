package com.summer.yunmusic.service;

import com.summer.yunmusic.dto.UserCreateDto;
import com.summer.yunmusic.dto.UserDto;
import com.summer.yunmusic.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * @author Summer
 * @since 2022/4/17 14:56
 */
public interface UserService extends UserDetailsService {

    List<UserDto> list();

    UserDto create(UserCreateDto userCreateDto);

    @Override
    User loadUserByUsername(String username) throws UsernameNotFoundException;
}
