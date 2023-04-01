package com.zhn.teamsharebackend.exception;

import com.zhn.teamsharebackend.constant.ErrorCode;

/**
 * 自定义业务异常
 *
 * @author zhn
 * @version 1.0
 */
public class BusinessException extends RuntimeException{
    private final int code;

    private final String description;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode, String message, String description) {
        super(message);
        this.code = errorCode.getCode();
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
