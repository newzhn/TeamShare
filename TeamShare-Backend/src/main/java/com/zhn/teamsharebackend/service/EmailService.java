package com.zhn.teamsharebackend.service;

/**
 * 邮件相关服务
 *
 * @author zhn
 * @version 1.0
 */
public interface EmailService {
    void sendVerificationCode(String toEmail,String code);
}
