package org.talk.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserLoginRequest {
    @NotNull(message = "Username can't be empty")
    private String username;
    @NotNull(message = "Password can't be empty")
    private String password;
}
