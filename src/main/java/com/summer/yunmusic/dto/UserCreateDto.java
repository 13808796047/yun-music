package com.summer.yunmusic.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Summer
 * @since 2022/4/18 0:50
 */
@Data
public class UserCreateDto {
    @NotBlank(message = "用户名不能为空")
    @Size(min = 4, max = 64, message = "用户名长度应该在4到64个字符之间")
    private String username;
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 8, message = "密码长度6到8位")
    private String password;
    private String nickname;
    private String gender;
}
