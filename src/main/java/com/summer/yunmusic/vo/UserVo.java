package com.summer.yunmusic.vo;

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

    private List<RoleVo> roles;
}
