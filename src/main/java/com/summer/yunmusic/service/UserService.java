package com.summer.yunmusic.service;

import com.summer.yunmusic.dto.UserCreateDto;
import com.summer.yunmusic.dto.UserDto;
import com.summer.yunmusic.dto.UserUpdateDto;
import com.summer.yunmusic.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Summer
 * @since 2022/4/17 14:56
 */
public interface UserService extends UserDetailsService {


    UserDto create(UserCreateDto userCreateDto);

    @Override
    User loadUserByUsername(String username) throws UsernameNotFoundException;

    UserDto get(String id);

    UserDto update(String id, UserUpdateDto userUpdateDto);

    void delete(String id);

    Page<UserDto> search(Pageable pageable);
}
