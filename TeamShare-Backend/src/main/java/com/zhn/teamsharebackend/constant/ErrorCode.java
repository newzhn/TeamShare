package com.zhn.teamsharebackend.constant;

/**
 * The enum Error code.
 *
 * @author zhn
 * @version 1.0
 */
public enum ErrorCode {
    // 错误码
    PARAMS_ERROR(40000,"请求参数错误",""),
    NULL_PARAMS_ERROR(40001,"请求数据为空",""),
    NOT_LOGIN(40100,"用户未登录",""),
    NOT_AUTH(40101,"没有权限进行该操作",""),
    NOT_USER(40102,"用户不存在",""),
    REGISTERED(40103,"用户已注册",""),
    PASSWORD_ERROR(40200,"登录密码错误",""),
    HANDLE_ERROR(50000,"请求处理错误",""),
    SYSTEM_ERROR(50001,"服务器运行出错","");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 状态码信息
     */
    private final String message;

    /**
     * 状态码描述
     */
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
