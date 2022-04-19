package com.summer.yunmusic.dto;

import com.summer.yunmusic.enums.Gender;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Summer
 * @since 2022/4/17 14:44
 */
@Data
public class UserDto {
    private String id;

    private String username;

    private String nickname;
    private List<RoleDto> roles;
    private Gender gender;
    private Boolean locked = false;
    private Boolean enabled = true;
    private String lastLoginIp;
    private Date lastLoginTime;
}
