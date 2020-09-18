package org.talk.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserRefreshRequest {
    @NotNull(message = "RefreshToken can't be empty")
    private String refreshToken;
}
