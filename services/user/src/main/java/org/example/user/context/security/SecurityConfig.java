package org.example.user.context.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

  @Bean
  SecurityWebFilterChain springSecurityFilterChain(
      ServerHttpSecurity http,
//      AuthenticationWebFilter authenticationWebFilter,
      JwtTokenFilter jwtTokenFilter,
      JwtTokenFilter jwtAuthenticationFilter) {
    var openPaths = new String[] {"api/users/v1/users/login"};

    return http.authorizeExchange(
            exchanges ->
                exchanges.pathMatchers(openPaths).permitAll().anyExchange().authenticated())
//        .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
        .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
        .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
        .csrf(ServerHttpSecurity.CsrfSpec::disable)
        .cors(ServerHttpSecurity.CorsSpec::disable)
        .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
        .addFilterAt(jwtTokenFilter, SecurityWebFiltersOrder.AUTHORIZATION)
        .build();
  }
}
