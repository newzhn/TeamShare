package com.zhn.teamsharebackend.domain.request;

import lombok.Data;

/**
 * @author zhn
 * @version 1.0
 */
@Data
public class LoginRequest {
    private String loginUsername;
    private String loginPassword;
}
