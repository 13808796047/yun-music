package com.summer.yunmusic.exception;

import lombok.Getter;

/**
 * 自定义异常处理类
 *
 * @author Summer
 * @since 2022/4/18 1:09
 */
@Getter
public class BizException extends RuntimeException {
    private final Integer code;

    public BizException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
    }
}
