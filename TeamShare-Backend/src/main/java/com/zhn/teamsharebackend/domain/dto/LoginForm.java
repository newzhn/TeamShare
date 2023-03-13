package com.zhn.teamsharebackend.domain.dto;

import lombok.Data;

/**
 * @author zhn
 * @version 1.0
 */
@Data
public class LoginForm {
    private String loginUsername;
    private String loginPassword;
}
