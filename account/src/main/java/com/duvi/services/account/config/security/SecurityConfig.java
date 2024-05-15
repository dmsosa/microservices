package com.duvi.services.account.config.security;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2ClientConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
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
                //allowing h2 to display in a frame of the same origin as our app
                .headers(headers -> headers.frameOptions(options -> options.sameOrigin()))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers( "/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/create").permitAll()
                        .requestMatchers(HttpMethod.GET, "/demo").permitAll()
                        .requestMatchers(HttpMethod.POST, "/demo").permitAll()
                        .requestMatchers(HttpMethod.GET, "/**").hasAuthority("SCOPE_profile")
                        .anyRequest().authenticated())
                //Very simple Security Config, just indicating the type of tokens supported by our server here.
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

        return ClientRegistration.withRegistrationId("accountClient")
                .clientId("accountClient")
                .clientSecret("account")
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .scope(List.of("openid", "profile"))
                .clientName("accountService")
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
