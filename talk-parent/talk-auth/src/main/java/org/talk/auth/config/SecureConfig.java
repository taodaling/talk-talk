package org.talk.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.talk.web.safe.JWTGenerator;

import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Configuration
public class SecureConfig {
    @Value("${jwt.privateKey}")
    private String privateKey;

    @Bean
    public JWTGenerator jwtGenerator() {
        try {
            byte[] secret = Base64.getDecoder().decode(privateKey.replaceAll("\\s", ""));
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(secret);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return new JWTGenerator(keyFactory.generatePrivate(keySpec));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
