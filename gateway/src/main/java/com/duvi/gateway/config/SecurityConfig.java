package com.duvi.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;

import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;


@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity serverHttpSecurity) throws Exception {
        serverHttpSecurity
                .csrf(csrf -> csrf.disable())
                .formLogin(Customizer.withDefaults())
                .oauth2Login(Customizer.withDefaults())
                .exceptionHandling(eh -> eh.authenticationEntryPoint(new RedirectServerAuthenticationEntryPoint("/oauth2/authorization/gatewayClient")))
                .authorizeExchange( exchanges ->
                        exchanges.pathMatchers("/login/**").permitAll()
                        .anyExchange().authenticated()
                 )
                .oauth2ResourceServer(rs -> rs.jwt(Customizer.withDefaults()));
        return serverHttpSecurity.build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails userDetails = User
                .withUsername("etwa")
                .password("$2a$10$pJqIMZXVdsMjTKXu4mIKreQQ/uNsaRm1SNx4YM9waF/9igekXX6Me")
                .build();
        return new MapReactiveUserDetailsService(userDetails);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
 }
