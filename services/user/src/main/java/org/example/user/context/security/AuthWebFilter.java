package org.example.user.context.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.stereotype.Component;

//@Component
//public class AuthWebFilter {
//  @Bean
//  public AuthenticationWebFilter authenticationWebFilter(
//      ReactiveAuthenticationManager manager,
//      ServerAuthenticationConverter jwtConverter,
//      ServerAuthenticationSuccessHandler successHandler,
//      ServerAuthenticationFailureHandler failureHandler) {
//    AuthenticationWebFilter filter = new AuthenticationWebFilter(manager);
//    filter.setRequiresAuthenticationMatcher(
//        exchange ->
//            ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "api/users/v1/users/login")
//                .matches(exchange));
//    filter.setServerAuthenticationConverter(jwtConverter);
//    filter.setAuthenticationSuccessHandler(successHandler);
//    filter.setAuthenticationFailureHandler(failureHandler);
//    filter.setSecurityContextRepository(NoOpServerSecurityContextRepository.getInstance());
//    return filter;
//  }
//}
