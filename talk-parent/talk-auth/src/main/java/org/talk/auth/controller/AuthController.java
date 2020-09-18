package org.talk.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.talk.auth.dto.*;
import org.talk.auth.service.AuthProcessService;
import org.talk.auth.service.AuthService;
import org.talk.web.annotation.LoginUser;
import org.talk.web.model.LoginUserModel;
import org.talk.web.model.WebResult;


@RequestMapping("/auth")
@RestController
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthProcessService authProcessService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public WebResult<UserLoginResponse> login(@RequestBody @Validated UserLoginRequest request) {
        return WebResult.newSuccess(authService.login(request.getUsername(), request.getPassword()));
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public WebResult<UserLoginResponse> refresh(@RequestBody @Validated UserRefreshRequest request) {
        return WebResult.newSuccess(authService.refresh(request.getRefreshToken()));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public WebResult<Void> register(@RequestBody @Validated UserRegisterRequest request) {
        authService.register(request);
        return WebResult.newSuccess();
    }

    @RequestMapping(value = "/verifyEmail", method = RequestMethod.POST)
    public WebResult<Void> verifyEmail(@RequestBody @Validated VerifyEmailRequest request) {
        authProcessService.verifyEmail(request.getJwt());
        return WebResult.newSuccess();
    }

    @RequestMapping(value = "/resendVerifyEmail", method = RequestMethod.POST)
    public WebResult<Void> resendVerifyEmail() {
//        authProcessService.verifyEmail(.getJwt());
        return WebResult.newSuccess();
    }
}
