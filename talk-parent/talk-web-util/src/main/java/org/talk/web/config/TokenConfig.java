package org.talk.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.talk.web.component.TokenHandlerMethodArgumentResolver;
import org.talk.web.safe.JWTParser;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;

@Configuration
public class TokenConfig implements WebMvcConfigurer {
    @Value("${jwt.publicKey}")
    private String publicKey;

    @Bean
    public JWTParser jwtParser() {
        try {
            byte[] secret = Base64.getDecoder().decode(publicKey.replaceAll("\\s", ""));
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(secret);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            //PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            publicKey = keyFactory.generatePublic(keySpec);
            return new JWTParser(publicKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new TokenHandlerMethodArgumentResolver());
    }
}