package it.alf.cleana.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
    private final Key key;
    private final long validityInMillis;
    private final String issuer;
    private final String audience;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-mins:60}") long expirationMinutes,
            @Value("${jwt.issuer:cleana}") String issuer,
            @Value("${jwt.audience:cleana}") String audience) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.validityInMillis = expirationMinutes * 60L * 1000L;
        this.issuer = issuer;
        this.audience = audience;
    }

    public String createToken(Long userId, String email) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityInMillis);

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("email", email)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setIssuer(issuer)
                .setAudience(audience)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
