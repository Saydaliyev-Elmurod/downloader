//package org.example.user.context.security;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.example.user.domain.User;
//import org.example.user.model.response.ResponseLogin;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
//import org.springframework.security.web.server.WebFilterExchange;
//import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
//@Component
//public class JwtAuthSuccessHandler implements ServerAuthenticationSuccessHandler {
//
//    private final ReactiveUserDetailsService userService;
//    private final ObjectMapper objectMapper;
//    private final JwtService jwtService;
//
//    public JwtAuthSuccessHandler(
//            ReactiveUserDetailsService userService, ObjectMapper objectMapper, JwtService jwtService) {
//        this.userService = userService;
//        this.objectMapper = objectMapper;
//        this.jwtService = jwtService;
//    }
//
//    @Override
//    public Mono<Void> onAuthenticationSuccess(
//            WebFilterExchange webFilterExchange, Authentication authentication) {
//        return Mono.defer(
//                        () -> {
//                            Object principal = authentication.getPrincipal();
//                            if (principal == null) {
//                                throw new RuntimeException("Unauthenticated");
//                            }
//
//                            if (principal instanceof User userPrincipal) {
//                                Integer id = userPrincipal.id();
//
//                                String accessToken = jwtService.generateAccessToken(id);
//                                String refreshToken = jwtService.generateRefreshToken(id);
//
//                                ResponseLogin responseLogin = new ResponseLogin(accessToken, refreshToken);
//
//                                ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
//                                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
//
//                                try {
//                                    byte[] responseData = objectMapper.writeValueAsBytes(responseLogin);
//                                    DataBuffer buffer = response.bufferFactory().wrap(responseData);
//                                    return response.writeWith(Mono.just(buffer)).then();
//                                } catch (JsonProcessingException e) {
//                                    throw new RuntimeException("Error while serializing response", e);
//                                }
//                            } else {
//                                throw new RuntimeException("Unauthorized");
//                            }
//                        })
//                .onErrorResume(
//                        error -> {
//                            ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
//                            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//                            return response.setComplete();
//                        });
//    }
//}
