package org.example.user.context.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.user.enums.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
  @Value("${app.security.jwt.secret-key}")
  private String secretKey;

  @Value("${app.security.jwt.access-expiration}")
  private long accessTokenExpireTime;

  @Value("${app.security.jwt.refresh-expiration}")
  private long refreshTokenExpireTime;

  @Value("${app.security.jwt.confirmation-expiration}")
  private long confirmationExpireTime;

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public List<SimpleGrantedAuthority> extractRoles(String token) {
    return extractClaim(
        token,
        claims -> {
          List<String> roles = claims.get("roles", List.class);
          return roles.stream()
              .map(Role::valueOf)
              .map(Enum::name)
              .map(SimpleGrantedAuthority::new)
              .toList();
        });
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(Integer username) {
    return buildToken(Map.of("roles", List.of(Role.USER)), username, accessTokenExpireTime);
  }

  public String generateRefreshToken(Integer username) {
    return buildToken(new HashMap<>(), username, refreshTokenExpireTime);
  }

  public String generateConfirmationToken(Map<String, Object> extraClaims, Integer username) {
    return buildToken(extraClaims, username, confirmationExpireTime);
  }

  public long accessTokenExpireTime() {
    return accessTokenExpireTime;
  }

  public long refreshTokenExpireTime() {
    return refreshTokenExpireTime;
  }

  private String buildToken(Map<String, Object> extraClaims, Integer userId, long expiration) {
    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(userId.toString())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public Date getExpireDate(String token) {
    return extractExpiration(token);
  }
}
