package com.summer.yunmusic.handler;

import com.summer.yunmusic.exception.BizException;
import com.summer.yunmusic.exception.ErrorResponse;
import com.summer.yunmusic.exception.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

/**
 * 全局异常处理类
 *
 * @author Summer
 * @since 2022/4/18 1:20
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 自定义异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BizException.class)
    public ErrorResponse bizExceptionHandler(BizException e) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(e.getCode());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTrace(e.getStackTrace());
        return errorResponse;
    }

    /**
     * 全局异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ErrorResponse exceptionHandler(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionEnum.INNER_ERROR.getCode());
        errorResponse.setMessage(ExceptionEnum.INNER_ERROR.getMessage());
        return errorResponse;
    }

    /**
     * 权限异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse accessDeniedHandler(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionEnum.FORBIDDEN.getCode());
        errorResponse.setMessage(ExceptionEnum.FORBIDDEN.getMessage());
        log.error(e.getMessage());
        return errorResponse;
    }

    /**
     * 验证异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse bizExceptionHandler(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        e.getBindingResult().getAllErrors().forEach(error -> {
            errorResponse.setCode(ExceptionEnum.BAD_REQUEST.getCode());
            errorResponse.setMessage(error.getDefaultMessage());
        });
        return errorResponse;
    }
}
