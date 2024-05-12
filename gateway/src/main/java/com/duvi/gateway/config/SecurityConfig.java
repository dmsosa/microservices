package com.duvi.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;


@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity serverHttpSecurity) throws Exception {

        serverHttpSecurity
                .csrf(csrf -> csrf.disable())
                .oauth2Login(spec -> spec.authenticationSuccessHandler(redirectSuccessHandler()))
                //redirect to Oauth2Server by default, oauth2server is in charge of login in and registering new users
                .exceptionHandling(exceptionHandlingSpec -> exceptionHandlingSpec.authenticationEntryPoint(new RedirectServerAuthenticationEntryPoint("/oauth2/authorization/gatewayClient")))
                .authorizeExchange( exchanges ->
                        exchanges
                        .pathMatchers("/css/**").permitAll()
                        .pathMatchers("/images/**").permitAll()
                        .pathMatchers("/js/**").permitAll()
                        .anyExchange().authenticated()
                 )
                .oauth2ResourceServer(rs -> rs.jwt(Customizer.withDefaults()));

        return serverHttpSecurity.build();
    }

    @Bean
    ServerAuthenticationSuccessHandler redirectSuccessHandler() {
        return new RedirectToIndexAuthenticationSuccessHandler();
    }
 }
