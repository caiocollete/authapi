package com.cc.authapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // desativa proteção CSRF (necessário em APIs REST)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // permite todas as requisições sem autenticação
                );

        return http.build();
    }
}