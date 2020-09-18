package org.talk.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserRegisterRequest {
    @NotNull(message = "Nickname can't be empty")
    private String nickname;
    @NotNull(message = "Username can't be empty")
    private String username;
    @NotNull(message = "Password can't be empty")
    private String password;
    @NotNull(message = "Email can't be empty")
    private String email;
    @NotNull(message = "Mobile can't be empty")
    private String mobile;
}
