package com.duvi.services.stats.config.security;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.client.ClientCredentialsOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private DiscoveryClient discoveryClient;
    public SecurityConfig(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(fop -> fop.sameOrigin()))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(rs -> rs.jwt(jwtConfigCustomizer()))
                .build();
    }
    @Bean
    Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer> jwtConfigCustomizer() {
        return new JwtCustomizer(discoveryClient);
    }
    @Bean
    public ClientRegistration clientRegistration() {
        Map<String, String> authUris = findAuthServiceUris();

        return ClientRegistration.withRegistrationId("statsClient")
                .clientId("statsClient")
                .clientSecret("stats")
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .scope(List.of("openid", "profile"))
                .clientName("statsService")
                .issuerUri(authUris.get("issuerUri"))
                .tokenUri(authUris.get("tokenUri"))
                .authorizationUri(authUris.get("authorizationUri"))
                .build();
    }

    private Map<String, String> findAuthServiceUris() {
        //finding auth instances
        ServiceInstance authInstance = discoveryClient.getInstances("auth-service").getFirst();

        Map<String, String> map = new HashMap<>();
        map.put("issuerUri", authInstance.getUri() + "/api/uaa");
        map.put("tokenUri", authInstance.getUri() + "/api/uaa/oauth2/token");
        map.put("authorizationUri", authInstance.getUri() + "/api/uaa/oauth2/authorize");
        return map;
    }

    @Bean
    ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(clientRegistration());
    }
}
