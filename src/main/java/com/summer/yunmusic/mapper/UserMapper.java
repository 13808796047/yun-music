package com.summer.yunmusic.mapper;

import com.summer.yunmusic.dto.UserCreateDto;
import com.summer.yunmusic.dto.UserDto;
import com.summer.yunmusic.entity.User;
import com.summer.yunmusic.vo.UserVo;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author Summer
 * @since 2022/4/17 14:50
 */
@Mapper(componentModel = "spring")
@Component
public interface UserMapper {
    // 实体转换为dto
    UserDto toDto(User user);

    // dto转换为vo
    UserVo toVo(UserDto userDto);

    // 转换为实体
    User createEntity(UserCreateDto userCreateDto);

}
