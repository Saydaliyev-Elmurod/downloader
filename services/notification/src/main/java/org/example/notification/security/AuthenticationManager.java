package org.example.notification.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import org.springframework.security.authentication.ReactiveAuthenticationManager;

import java.util.Collections;

@Slf4j
@Component
@AllArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    public Mono<Authentication> authenticate(final Authentication authentication) {
        String token = authentication.getCredentials().toString();

        Mono<UsernamePasswordAuthenticationToken> usernamePasswordAuthenticationTokenMono = jwtUtil.getAllClaimsFromToken(token)
                .flatMap(claims -> {
                    String username = claims.getSubject();
                    if (username == null) {
                        return Mono.error(new BadCredentialsException("Invalid token"));
                    }

                    return Mono.justOrEmpty(userDetailsService.loadUserByUsername(username))
                            .flatMap(userDetails -> {
                                if (jwtUtil.validateToken(token).block()) {
                                    return Mono.just(new UsernamePasswordAuthenticationToken(
                                            userDetails,
                                            null,
                                            Collections.singletonList(new SimpleGrantedAuthority(claims.get(JWTUtil.KEY_ROLE).toString()))
                                    ));
                                } else {
                                    return Mono.error(new BadCredentialsException("Invalid token"));
                                }
                            });
                }).onErrorResume(e -> {
                    log.error("Authentication error: {}", e.getMessage());
                    return Mono.empty();
                });
    }
}
