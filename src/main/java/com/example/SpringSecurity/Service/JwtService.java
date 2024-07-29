package com.example.SpringSecurity.Service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    private static final String SecretKey = "14D4093A539EA22D1C092EC1BF7BF14E71A93FAB808F0091E2AA65B0C5D66B45";
    public static final long VALIDITY = 30 * 60000;

    public String generateToken(UserDetails userDetails) {
        Map<String, String> claims = new HashMap<>();
        claims.put("sub", "1234567890");
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                .signWith(getSecretKey()).compact();

    }

    private SecretKey getSecretKey() {
        byte[] decodedSecretKey = Base64.getDecoder().decode(SecretKey);
        return Keys.hmacShaKeyFor(decodedSecretKey);
    }
}
