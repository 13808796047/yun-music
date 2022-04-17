package com.summer.yunmusic.exception;

import lombok.Data;

/**
 * 错误响应类
 *
 * @author Summer
 * @since 2022/4/18 1:21
 */
@Data
public class ErrorResponse {
    private Integer code;
    private String message;
    private Object trace;
}
