package com.zhn.teamsharebackend.exception;

import com.zhn.teamsharebackend.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * GlobalExceptionHandler
 * @author zhn
 * @version 1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result businessExceptionHandler(BusinessException e) {
        log.error("BusinessException:{Message: " + e.getMessage() + "," +
                "Description: " + e.getDescription() + "}",e);
        return Result.fail(e.getCode(),e.getMessage(),e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public Result runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException",e);
        return Result.fail(ErrorCode.SYSTEM_ERROR,e.getMessage());
    }
}
