package org.example.user.context.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

//@Component
//class JwtAuthFailureHandler implements ServerAuthenticationFailureHandler {
//
//  @Override
//  public Mono<Void> onAuthenticationFailure(
//      WebFilterExchange webFilterExchange, AuthenticationException exception) {
//    ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
//
//    response.setStatusCode(HttpStatus.UNAUTHORIZED);
//
//    return response.setComplete();
//  }
//}
