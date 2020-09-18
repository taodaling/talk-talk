package org.talk.web.safe;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.Map;

public class JWTGenerator {
    private PrivateKey privateKey;

    public JWTGenerator(PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.privateKey = privateKey;
    }

    public String generate(Map<String, Object> claims, long ttl) {
        Date now = new Date();
        Date expire = new Date(now.getTime() + ttl);
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setHeaderParam("type", "JWT")
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .setExpiration(expire);
        return builder.compact();
    }
}
