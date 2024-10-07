package org.example.exam.context.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(
            ServerHttpSecurity http,
            JwtTokenFilter jwtTokenFilter,
            JwtTokenFilter jwtAuthenticationFilter) {
        var openPaths = new String[]{
                "/api/users/v1/users/login", "/api/users/v1/users",
                "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**",
                "/webjars/**","/swagger-resources/**","/v3/api-docs"
        };


        return http.authorizeExchange(
                        exchanges ->
                                exchanges.pathMatchers(openPaths).permitAll().anyExchange().authenticated())
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .addFilterAt(jwtTokenFilter, SecurityWebFiltersOrder.AUTHORIZATION)
                .build();
    }
}
