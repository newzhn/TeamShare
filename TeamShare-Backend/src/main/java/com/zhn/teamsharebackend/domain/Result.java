package com.zhn.teamsharebackend.domain;

import com.zhn.teamsharebackend.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 通用结果返回类
 *
 * @param <T> the type parameter
 * @author zhn
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class Result<T> {
    /**
     * 结果状态码
     */
    private int code;

    /**
     * 返回内容
     */
    private T data;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回结果描述
     */
    private String description;

    public static <T> Result<T> ok(T data,String message,String description) {
        return new Result<T>(200,data,message,description);
    }

    public static <T> Result<T> ok(T data,String message) {
        return new Result<T>(200,data,message,"");
    }

    public static <T> Result<T> ok(T data) {
        return new Result<T>(200,data,"","");
    }

    public static <T> Result<T> ok() {
        return new Result<T>(200,null,"","");
    }

    public static <T> Result<T> fail(int code,String message,String description) {
        return new Result<T>(code ,null,message,description);
    }

    public static <T> Result<T> fail(ErrorCode errorCode) {
        return new Result<T>(errorCode.getCode(),null,errorCode.getMessage(),errorCode.getDescription());
    }

    public static <T> Result<T> fail(ErrorCode errorCode,T data) {
        return new Result<T>(errorCode.getCode(),data,errorCode.getMessage(),errorCode.getDescription());
    }

    public static <T> Result<T> fail(ErrorCode errorCode,String message) {
        return new Result<T>(errorCode.getCode(),null,message,errorCode.getDescription());
    }

    public static <T> Result<T> fail(ErrorCode errorCode,T data,String message) {
        return new Result<T>(errorCode.getCode(),data,message,errorCode.getDescription());
    }

    public static <T> Result<T> fail(ErrorCode errorCode,String message,String description) {
        return new Result<T>(errorCode.getCode(),null,message,description);
    }

    public static <T> Result<T> fail(ErrorCode errorCode,T data,String message,String description) {
        return new Result<T>(errorCode.getCode(),data,message,description);
    }
}
