package com.helpdesk.support_system.security.jwt.service;

import com.helpdesk.support_system.user.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String secret;

    private SecretKey generateKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T>T extractToken(String token, Function<Claims, T> claimResolver) {
        return claimResolver.apply(extractAllClaims(token));
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("roles", user.getRoles()
                .stream()
                .map(role -> "ROLE_" + role.name())
                .toList()
        );

        return Jwts
                .builder()
                .claims()
                .add(claims)
                .subject(user.getUsername())
                .issuer("Mokoena")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60*60*1000))
                .and()
                .signWith(generateKey())
                .compact();

    }

    public List<String> extractRoles(String token) {
        return extractToken(token, claims -> claims.get("roles", List.class));
    }

    public String extractUsername(String token) {
        return extractToken(token, Claims::getSubject);
    }

    public boolean isValid(String token, String username) {
        String tokenUsername = extractUsername(token);
        Date expiration = extractToken(token, Claims::getExpiration);
        return (tokenUsername.equals(username) && !expiration.before(new Date()));
    }

}
