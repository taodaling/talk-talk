package org.talk.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class VerifyEmailRequest {
    @NotNull(message = "Jwt can't be null")
    private String jwt;
}
