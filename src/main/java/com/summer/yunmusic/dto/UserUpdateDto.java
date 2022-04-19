package com.summer.yunmusic.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @Author Summer
 * @Since 2022/4/19 4:45 PM
 * @Version 1.0
 */
@Data
public class UserUpdateDto {
    @NotBlank(message = "用户名不能为空")
    @Size(min = 4, max = 64, message = "用户名长度应该在4到64个字符之间")
    private String username;
    private String nickname;
    private String gender;
}
