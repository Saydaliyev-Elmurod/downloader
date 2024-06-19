package org.example.user.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.example.user.security.ListUtils.toSingleton;

@Service
public class JWTUtil {

    @Value("${jjwt.secret}")
    private String secret;

    @Value("${token.expiration.in.hour}")
    private int expirationTimeInHour;

    private Key key;

    public static final String KEY_ROLE = "role";

    public Mono<Claims> getAllClaimsFromToken(String token) {
        return Mono.fromCallable(() ->Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload());
    }
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails) {
        String authority = userDetails.getAuthorities()
                                      .stream()
                                      .map(GrantedAuthority::getAuthority)
                                      .collect(toSingleton());

        Map<String, Object> claims = new HashMap<>();
        claims.put(KEY_ROLE, authority);

        return Jwts.builder()
                   .setClaims(claims)
                   .setSubject(userDetails.getUsername())
                   .setExpiration(Date.from(Instant.now().plus(Duration.ofHours(expirationTimeInHour))))
                   .setIssuedAt(Date.from(Instant.now()))
                   .signWith(getKey())
                   .compact();
    }

    public Mono<Boolean> validateToken(String token) {
        return getAllClaimsFromToken(token)
                .map(Claims::getExpiration)
                .map(expiration -> expiration.after(new Date()))
                .onErrorReturn(false);
    }

    private Key getKey() {
        if (key == null) {
            synchronized (this) {
                if (key == null) {
                    key = Keys.hmacShaKeyFor(secret.getBytes());
                }
            }
        }
        return key;
    }
}
