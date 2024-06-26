package com.duvi.gateway.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RefreshScope
public class JwtCustomizer implements Customizer<ServerHttpSecurity.OAuth2ResourceServerSpec.JwtSpec> {

    private static Logger logger = LoggerFactory.getLogger(JwtCustomizer.class);
    @Value("${app.issuer-uri}")
    private String hostIssuerUri;
    private DiscoveryClient discoveryClient;
    private OAuth2TokenValidator<Jwt> jwtValidator = new JwtValidator();

    public JwtCustomizer(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Bean
    public ReactiveJwtDecoder reactiveJwtDecoder() {
        String issuerUri = this.findIssuerUri();
        NimbusReactiveJwtDecoder jwtDecoder = (NimbusReactiveJwtDecoder) ReactiveJwtDecoders.fromIssuerLocation(issuerUri);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuerUri);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, jwtValidator);
        jwtDecoder.setJwtValidator(withAudience);
        return jwtDecoder;
    }

    @Override
    public void customize(ServerHttpSecurity.OAuth2ResourceServerSpec.JwtSpec jwtSpec) {
        if (logger.isTraceEnabled()) {
            logger.trace("Customizing jwtConfigurer ...");
        }

        jwtSpec.jwtDecoder(reactiveJwtDecoder());
    }
    private String findIssuerUri() {
        List<ServiceInstance> authInstances = discoveryClient.getInstances("auth-service");
        ServiceInstance authService = authInstances.getFirst();
        String issuerUri = !hostIssuerUri.isBlank() ? hostIssuerUri : authService.getUri().toString();
        return issuerUri + "/api/uaa";
    }

    static class JwtValidator implements OAuth2TokenValidator<Jwt> {
        private static Logger logger = LoggerFactory.getLogger(JwtValidator.class);

        static OAuth2Error error = new OAuth2Error("invalid_audience", "The audience for this token is incorrect", null);

        public JwtValidator() {}

        @Override
        public OAuth2TokenValidatorResult validate(Jwt token) {
            if (token.getAudience().contains("gateway-service")) {
                if (logger.isTraceEnabled()) {
                    logger.trace("gateway-service audience found, Bruder!, token is valid");
                }
                return OAuth2TokenValidatorResult.success();
            } else {
                return OAuth2TokenValidatorResult.failure(error);
            }
        }
    }
}