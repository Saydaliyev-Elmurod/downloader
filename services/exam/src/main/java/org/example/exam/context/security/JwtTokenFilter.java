package org.example.exam.context.security;

import jakarta.annotation.Nonnull;
import org.example.exam.mapper.UserMapper;
import org.example.exam.model.UserPrincipal;
import org.example.exam.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.List;
import java.util.Objects;

@Component

public class JwtTokenFilter implements WebFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtTokenFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    @Nonnull
    public Mono<Void> filter(@Nonnull ServerWebExchange exchange,@Nonnull WebFilterChain chain) {
        String token = jwtAccessToken(exchange);

        if (token == null) {
            return chain.filter(exchange);
        }

        Integer userId = Integer.valueOf(jwtService.extractUsername(token));
        return userRepository.findById(userId)
                .flatMap(userEntity -> {
                    if (userEntity != null) {
                        List<SimpleGrantedAuthority> roles = jwtService.extractRoles(token);

                        UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(
                                        new UserPrincipal(UserMapper.INSTANCE.toResponse(userEntity), token),
                                        null, roles);

                        Context context = ReactiveSecurityContextHolder.withAuthentication(auth);
                        return chain.filter(exchange).contextWrite(context);
                    } else {
                        // If user is not found, proceed without authentication
                        return chain.filter(exchange);
                    }
                })
                .switchIfEmpty(chain.filter(exchange));
    }


    private String jwtAccessToken(ServerWebExchange exchange) {
        try {
            return Objects.requireNonNull(exchange
                            .getRequest()
                            .getHeaders()
                            .getFirst("Authorization"))
                    .substring("Bearer ".length());
        } catch (Exception e) {
            return null;
        }
    }
}
