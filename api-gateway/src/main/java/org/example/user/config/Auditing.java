package org.example.user.config;

//import org.springframework.context.annotation.Bean;
//import org.springframework.data.domain.ReactiveAuditorAware;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.security.core.userdetails.User;

//public class Auditing {
//    @Bean
//    ReactiveAuditorAware<String> auditorAware() {
//        return () -> ReactiveSecurityContextHolder.getContext()
//                .map(SecurityContext::getAuthentication)
//                .filter(Authentication::isAuthenticated)
//                .map(Authentication::getPrincipal)
//                .map(User.class::cast)
//                .map(User::getUsername);
//    }
//}
