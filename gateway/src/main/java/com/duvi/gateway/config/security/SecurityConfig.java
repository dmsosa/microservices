package com.duvi.gateway.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;

import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {
    @Value("${eureka.instance.instance-id}")
    private String instanceId;

    private DiscoveryClient discoveryClient;
    public SecurityConfig(DiscoveryClient discoveryClient ) {
        this.discoveryClient = discoveryClient;
    }

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
                .oauth2Client(Customizer.withDefaults())
                .oauth2ResourceServer(rs -> rs.jwt(jwt -> jwtConfigCustomizer()));

        return serverHttpSecurity.build();
    }

    @Bean
    ServerAuthenticationSuccessHandler redirectSuccessHandler() {
        return new RedirectToIndexAuthenticationSuccessHandler();
    }
    @Bean
    ServerAuthenticationFailureHandler failureHandler() {
        return new ServerAuthenticationFailureHandler() {
            @Override
            public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
                exception.printStackTrace();
                return Mono.empty();
            }
        };
    }

    @Bean
    Customizer<ServerHttpSecurity.OAuth2ResourceServerSpec.JwtSpec> jwtConfigCustomizer() {
        return new JwtCustomizer(discoveryClient);
    }

    @Bean
    public ClientRegistration clientRegistration() {

        Map<String, String> authUris = findAuthServiceUris();
        String redirectUri = findCurrentInstanceUri() + "/login/oauth2/code/gatewayClient";

        return ClientRegistration.withRegistrationId("gatewayClient")
                .clientId("gatewayClient")
                .clientSecret("gateway")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .scope(List.of("openid", "profile"))
                .clientName("gatewayService")
                .issuerUri(authUris.get("issuerUri"))
                .tokenUri(authUris.get("tokenUri"))
                .authorizationUri(authUris.get("authorizationUri"))
                .redirectUri(redirectUri)
                .jwkSetUri(authUris.get("jwkSetUri"))
                .build();
    }

    private Map<String, String> findAuthServiceUris() {
        //finding auth instances
        ServiceInstance authInstance = discoveryClient.getInstances("auth-service").getFirst();
        String authServerUri = authInstance.getUri().toString();
        Map<String, String> map = new HashMap<>();
        map.put("issuerUri", authServerUri + "/api/uaa");
        map.put("tokenUri", authServerUri + "/api/uaa/oauth2/token");
        map.put("authorizationUri", authServerUri + "/api/uaa/oauth2/authorize");
        map.put("jwkSetUri", authServerUri + "/api/uaa/oauth2/jwks");
        map.put("userInfoUri", authServerUri + "/api/uaa/userinfo");
        return map;
    }
    private String findCurrentInstanceUri() {
        return "http://127.0.0.1:8061";
    }

    @Bean
    ReactiveClientRegistrationRepository clientRegistrationRepository(ClientRegistration registration) {
        return new InMemoryReactiveClientRegistrationRepository(registration);
    }
 }
