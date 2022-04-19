package com.summer.yunmusic.vo;

import com.summer.yunmusic.enums.Gender;
import lombok.Data;

import java.util.List;

/**
 * @author Summer
 * @since 2022/4/17 14:38
 */
@Data
public class UserVo {
    private String id;
    private String username;

    private String nickname;

    private Gender gender;
    private Boolean locked;
    private Boolean enabled;
    private List<RoleVo> roles;
}
