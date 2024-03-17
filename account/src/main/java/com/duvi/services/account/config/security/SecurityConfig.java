package com.duvi.services.account.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(csrf -> csrf.disable())
                //allowing h2 to display in a frame of the same origin as our app
                .headers(headers -> headers.frameOptions(options -> options.sameOrigin()))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers( "/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/demo").permitAll()
                        .requestMatchers(HttpMethod.POST, "/demo").permitAll()
                        .requestMatchers(HttpMethod.POST, "/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/**").hasAuthority(OidcScopes.PROFILE)
                        .anyRequest().authenticated())
                .oauth2ResourceServer(rs -> rs.jwt(Customizer.withDefaults()))
                .build();
    }
}
