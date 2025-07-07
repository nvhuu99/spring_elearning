package com.demo.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@AllArgsConstructor
public class JwtUtil {
    private final Environment env;

    public String generateToken(String username, String accessToken) {
        return Jwts.builder()
            .setSubject(username)
            .claim("accessToken", accessToken)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 24 * 1000 * 60 * 60))
            .signWith(Keys.hmacShaKeyFor(getSecret().getBytes()), SignatureAlgorithm.HS256)
            .compact();
    }

    public Jws<Claims> parseToken(String token) throws Exception{
        return Jwts.parserBuilder()
                .setSigningKey(getSecret().getBytes())
                .build()
                .parseClaimsJws(token);
    }

    public String extractUsername(Jws<Claims> parsed) {
        return parsed.getBody().getSubject();
    }

    public String extractAccessToken(Jws<Claims> parsed) {
        return parsed.getBody().get("accessToken").toString();
    }

    private String getSecret() {
        return env.getProperty("jwt.secret");
    }
}
