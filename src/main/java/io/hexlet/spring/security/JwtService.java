package io.hexlet.spring.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final String SECRET =
            "veryVerySecretKeyForJwtToken123456";

    public String generateToken(
            String email
    ) {

        return Jwts.builder()
                .setSubject(email)
                .signWith(
                        Keys.hmacShaKeyFor(
                                SECRET.getBytes()
                        ),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    public String extractEmail(
            String token
    ) {

        return Jwts.parserBuilder()
                .setSigningKey(
                        Keys.hmacShaKeyFor(
                                SECRET.getBytes()
                        )
                )
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}