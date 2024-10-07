package org.example.user.context.security;

import jakarta.annotation.Nonnull;
import org.example.user.mapper.UserMapper;
import org.example.user.model.UserPrincipal;
import org.example.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
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
import java.util.UUID;

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
    public Mono<Void> filter(@Nonnull ServerWebExchange exchange, @Nonnull WebFilterChain chain) {
        String token = jwtAccessToken(exchange);

        if (token == null) {
            return chain.filter(exchange); // Token mavjud bo'lmasa, davom ettiramiz
        }

        UUID userId;
        try {
            userId = UUID.fromString(jwtService.extractUsername(token)); // Token ichidagi userni olish
        } catch (Exception e) {
            return unauthorizedResponse(exchange); // Noto'g'ri yoki eskirgan token
        }

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
                        // User topilmasa, davom etamiz
                        return chain.filter(exchange);
                    }
                })
                .switchIfEmpty(chain.filter(exchange)) // User topilmasa, hech qanday autentifikatsiya qilmaslik
                .onErrorResume(e -> unauthorizedResponse(exchange)); // JWT xatolarini ushlash
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange) {
        // Javobga 401 Unauthorized statusini qo'yish
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
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
