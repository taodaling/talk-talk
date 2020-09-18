package org.talk.auth.service;

import io.jsonwebtoken.Claims;
import org.apache.catalina.startup.UserConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.talk.auth.consts.UserConst;
import org.talk.auth.dto.UserLoginResponse;
import org.talk.auth.dto.UserRegisterRequest;
import org.talk.auth.model.User;
import org.talk.auth.stream.UserRegisterProcessStream;
import org.talk.web.exception.BizException;
import org.talk.web.exception.ResourceNotFoundException;
import org.talk.web.safe.JWTGenerator;
import org.talk.web.safe.JWTParser;

import java.util.BitSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RefreshScope
@EnableBinding(UserRegisterProcessStream.class)
public class AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JWTGenerator jwtGenerator;
    @Value("${auth.accessToken.ttl}")
    private Long accessTokenTTL;
    @Value("${auth.refreshToken.ttl}")
    private Long refreshTokenTTL;
    @Autowired
    private JWTParser jwtParser;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRegisterProcessStream userRegisterProcessStream;

    public static void assertUserLoginAble(User user) {
        if(!UserConst.getEmailVerified(user)){
            throw new BizException("用户");
        }
    }

    public UserLoginResponse login(String username, String password) {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("Wrong username");
        }
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new BizException("Wrong password");
        }
        return generate(user.getId(), user.getUsername());
    }

    private UserLoginResponse generate(Integer userId, String username) {
        Map<String, Object> summary = new HashMap<>();
        summary.put("userid", userId);
        summary.put("username", username);
        summary.put("type", "auth");
        UserLoginResponse result = new UserLoginResponse();
        result.setAccessToken(jwtGenerator.generate(summary, accessTokenTTL));
        result.setRefreshToken(jwtGenerator.generate(summary, refreshTokenTTL));
        result.setExpireAt(new Date(System.currentTimeMillis() + accessTokenTTL));
        result.setIssuedAt(new Date());
        return result;
    }


    public UserLoginResponse refresh(String refreshToken) {
        Claims claims = jwtParser.parse(refreshToken);
        return generate(claims.get("userid", Integer.class), claims.get("username", String.class));
    }

    public Integer register(UserRegisterRequest request) {
        if (userService.findByEmail(request.getEmail()) != null) {
            throw new BizException("Email has been used by others");
        }
        if (userService.findByUsername(request.getUsername()) != null) {
            throw new BizException("Username has been used by others");
        }
        if (userService.findByMobile(request.getUsername()) != null) {
            throw new BizException("Mobile has been used by others");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setNickname(request.getNickname());
        user.setMobile(request.getMobile());
        user.setAddTime(new Date());
        user.setUpdateTime(new Date());
        Integer id = userService.addUser(user);

        userRegisterProcessStream.output().send(
                MessageBuilder.withPayload(id).build());
        return id;
    }


}
