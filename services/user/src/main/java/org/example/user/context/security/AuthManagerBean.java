package org.example.user.context.security;

import org.example.user.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//@Component
//public class AuthManagerBean {
//  @Bean
//  public ReactiveAuthenticationManager reactiveAuthenticationManager(
//          UserServiceImpl userService, PasswordEncoder passwordEncoder) {
//    UserDetailsRepositoryReactiveAuthenticationManager manager =
//        new UserDetailsRepositoryReactiveAuthenticationManager(userService);
//    manager.setPasswordEncoder(passwordEncoder);
//    return manager;
//  }
//}
