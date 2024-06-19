package org.example.user.context.security;

import org.example.user.model.request.RequestLogin;
import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
//
//@Component
//public class JwtToAuthConverter implements ServerAuthenticationConverter {
//
//  private final Jackson2JsonDecoder jacksonDecoder;
//
//  public JwtToAuthConverter(Jackson2JsonDecoder jacksonDecoder) {
//    this.jacksonDecoder = jacksonDecoder;
//  }
//
//  @Override
//  public Mono<Authentication> convert(ServerWebExchange exchange) {
//    var dataBuffer = exchange.getRequest().getBody();
//    var type = ResolvableType.forClass(RequestLogin.class);
//    return jacksonDecoder
//        .decodeToMono(dataBuffer, type, MediaType.APPLICATION_JSON, null)
//        .cast(RequestLogin.class)
//        .map(
//            requestLogin ->
//                new UsernamePasswordAuthenticationToken(
//                    requestLogin.username(), requestLogin.password()));
//  }
//}
