package org.talk.web.safe;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.security.PublicKey;

public class JWTParser {
    private PublicKey publicKey;

    public JWTParser(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public Claims parse(String jwt) {
        return Jwts.parser()
                .setSigningKey(publicKey)
                .parseClaimsJws(jwt).getBody();
    }
}
