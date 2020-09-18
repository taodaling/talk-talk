package org.talk.auth.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserLoginResponse {
    private String accessToken;
    private String refreshToken;
    private Date expireAt;
    private Date issuedAt;
}
