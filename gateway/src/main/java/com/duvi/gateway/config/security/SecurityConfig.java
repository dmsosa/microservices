package com.duvi.gateway.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;

import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;

import org.springframework.security.oauth2.client.web.server.DefaultServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.server.OAuth2AuthorizationRequestRedirectWebFilter;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.csrf.CsrfWebFilter;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {
    @Value("${eureka.instance.instance-id}")
    private String instanceId;
    @Value("${app.issuer-uri}")
    private String hostIssuerUri;
    private final String[] WHITELIST = {"/v3/api-docs/**", "/swagger-ui/**"};
    private DiscoveryClient discoveryClient;
    public SecurityConfig(DiscoveryClient discoveryClient ) {
        this.discoveryClient = discoveryClient;
    }

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity serverHttpSecurity) throws Exception {
        serverHttpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(corsSpec -> corsSpec.configurationSource(corsConfigurationSource()))
                .oauth2Login(spec -> spec
                        .authenticationSuccessHandler(redirectSuccessHandler())
                        .authenticationFailureHandler(failureHandler())
                        .authorizationRequestResolver(customAuthorizationRequestResolver()))
                //redirect to Oauth2Server by default, oauth2server is in charge of login in and registering new users
                .exceptionHandling(exceptionHandlingSpec -> exceptionHandlingSpec.authenticationEntryPoint(new RedirectServerAuthenticationEntryPoint("/oauth2/authorization/gatewayClient")))
                .authorizeExchange( exchanges ->
                        exchanges
                        .pathMatchers(WHITELIST).permitAll()
                        .pathMatchers("/css/**").permitAll()
                        .pathMatchers("/images/**").permitAll()
                        .pathMatchers("/js/**").permitAll()
                        .anyExchange().authenticated()
                 )
                .logout(logoutSpec -> logoutSpec
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(oidcLogoutSuccessHandler()))
                .oauth2Client(Customizer.withDefaults())
                .oauth2ResourceServer(rs -> rs.jwt(jwt -> jwtConfigCustomizer()));
        return serverHttpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type"));
        configuration.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public ServerOAuth2AuthorizationRequestResolver customAuthorizationRequestResolver() {
        DefaultServerOAuth2AuthorizationRequestResolver resolver = new DefaultServerOAuth2AuthorizationRequestResolver(clientRegistrationRepository());
        resolver.setAuthorizationRequestCustomizer(new AuthRequestResolverCustomizer());
        return resolver;
    }

    @Bean
    public ServerAuthenticationSuccessHandler redirectSuccessHandler() {
        return new AuthSuccessHandler();
    }
    @Bean
    public ServerAuthenticationFailureHandler failureHandler() {
        return new AuthFailureHandler();
    }

    @Bean
    public ServerLogoutSuccessHandler oidcLogoutSuccessHandler() {
        OidcClientInitiatedServerLogoutSuccessHandler oidcLogoutSuccessHandler =
                new OidcClientInitiatedServerLogoutSuccessHandler(this.clientRegistrationRepository());
        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseURL}/index");
        return oidcLogoutSuccessHandler;
    }

    @Bean
    public Customizer<ServerHttpSecurity.OAuth2ResourceServerSpec.JwtSpec> jwtConfigCustomizer() {
        return new JwtCustomizer(discoveryClient);
    }

    @Bean
    public ClientRegistration clientRegistration() {

        Map<String, String> authUris = findAuthServiceUris();
        String redirectUri = "http://127.0.0.1:8061/login/oauth2/code/gatewayClient";

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

    @RefreshScope
    private Map<String, String> findAuthServiceUris() {
        //finding auth instances
        ServiceInstance authInstance = discoveryClient.getInstances("auth-service").getFirst();
        String issuerUri = !hostIssuerUri.isBlank() ? hostIssuerUri : authInstance.getUri().toString();
        Map<String, String> map = new HashMap<>();
        map.put("issuerUri", issuerUri + "/api/uaa");
        map.put("tokenUri", issuerUri + "/api/uaa/oauth2/token");
        map.put("authorizationUri", issuerUri + "/api/uaa/oauth2/authorize");
        map.put("jwkSetUri", issuerUri + "/api/uaa/oauth2/jwks");
        map.put("userInfoUri", issuerUri + "/api/uaa/userinfo");
        return map;
    }


    @Bean
    public ReactiveClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryReactiveClientRegistrationRepository(clientRegistration());
    }
 }
