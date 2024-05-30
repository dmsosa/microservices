package com.duvi.services.noti.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtCustomizer implements Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer> {

    private static Logger logger = LoggerFactory.getLogger(JwtCustomizer.class);
    private DiscoveryClient discoveryClient;
    private OAuth2TokenValidator<Jwt> jwtValidator = new JwtValidator();

    public JwtCustomizer(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }
    @Override
    public void customize(OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer jwtConfigurer) {
        if (logger.isTraceEnabled()) {
            logger.trace("Customizing jwtConfigurer ...");
        }
        String issuerUri = this.findIssuerUri();
        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromIssuerLocation(issuerUri);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuerUri);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, jwtValidator);
        jwtDecoder.setJwtValidator(withAudience);
        jwtConfigurer.decoder(jwtDecoder);
    }

    private String findIssuerUri() {
        List<ServiceInstance> authInstances = discoveryClient.getInstances("auth-service");
        ServiceInstance authService = authInstances.getFirst();
        return authService.getUri().toString() + "/api/uaa";
    }

    static class JwtValidator implements OAuth2TokenValidator<Jwt> {
        private static Logger logger = LoggerFactory.getLogger(JwtValidator.class);

        static OAuth2Error error = new OAuth2Error("invalid_audience", "The audience for this token is incorrect", null);

        public JwtValidator() {}

        @Override
        public OAuth2TokenValidatorResult validate(Jwt token) {
            if (token.getAudience().contains("noti-service")) {
                if (logger.isTraceEnabled()) {
                    logger.trace("noti-service audience found, Bruder!, token is valid");
                }
                return OAuth2TokenValidatorResult.success();
            } else {
                return OAuth2TokenValidatorResult.failure(error);
            }
        }
    }
}
