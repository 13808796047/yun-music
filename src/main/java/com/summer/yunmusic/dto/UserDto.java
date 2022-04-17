package com.summer.yunmusic.dto;

import com.summer.yunmusic.vo.RoleVo;
import lombok.Data;

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

    private List<RoleVo> roles;
}
