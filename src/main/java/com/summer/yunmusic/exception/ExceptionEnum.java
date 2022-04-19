package com.summer.yunmusic.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Summer
 * @since 2022/4/18 1:11
 */
@AllArgsConstructor
@Getter
public enum ExceptionEnum {
    INNER_ERROR(500, "系统内部错误"),
    UNAUTHORIZED(401, "未登录"),
    BAD_REQUEST(400, "请求错误"),
    FORBIDDEN(403, "无权操作"),
    NOT_FOUND(404, "未找到"),
    USER_NAME_DUPLICATE(40001001, "用户名重复"),
    USER_NOT_FOUND(40401002, "用户不存在"),
    USER_PASSWORD_NOT_MATCH(40401003, "用户名或密码错误"),
    USER_NOT_ENABLED(50001001, "用户未启用"),
    USER_LOCKED(50001002, "用户被锁定");

    private final Integer code;
    private final String message;

}
