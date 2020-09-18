package org.talk.auth.service;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.talk.auth.model.User;
import org.talk.auth.stream.UserRegisterProcessStream;
import org.talk.web.exception.BizException;
import org.talk.web.safe.JWTGenerator;
import org.talk.web.safe.JWTParser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@EnableBinding(UserRegisterProcessStream.class)
@Slf4j
@RefreshScope
public class AuthProcessService {
    @Autowired
    private UserService userService;
    @Value("${host}")
    private String host;
    @Autowired
    private JWTGenerator generator;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private String hashKeyPrefix = "registerVerifyEmail-";
    @Value("${auth.verifyEmail.interval}")
    private Long verifyEmailInterval;
    @Autowired
    private JWTParser jwtParser;

    private static final String TYPE = "verifyEmail";

    @StreamListener(UserRegisterProcessStream.CHANNEL)
    public void sendVerifyEmail(@Payload Integer userId) {
        if (!redisTemplate.opsForValue().setIfAbsent(hashKeyPrefix + userId, null, verifyEmailInterval,
                TimeUnit.MINUTES)) {
            throw new BizException("Operation too frequently, please wait at least " + verifyEmailInterval + " minutes");
        }

        User user = userService.findById(userId);
        String email = user.getEmail();

        Map<String, Object> params = new HashMap<>();
        params.put("userid", userId);
        params.put("type", TYPE);
        String url = host + "/auth/verifyEmail?jwt=" + generator.generate(params, TimeUnit.DAYS.toMillis(1));
        log.trace("send verification email {} to {}", url, email);
    }

    public void verifyEmail(String jwt) {
        Claims claims = jwtParser.parse(jwt);
        if (!TYPE.equals(claims.get("type", String.class))) {
            throw new IllegalArgumentException();
        }

        //active user
        Integer userId = claims.get("userid", Integer.class);
        userService.setUserEmailVerified(Collections.singletonList(userId));
    }
}
