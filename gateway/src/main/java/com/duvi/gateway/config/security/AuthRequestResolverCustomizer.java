package com.duvi.gateway.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import java.util.function.Consumer;

public class AuthRequestResolverCustomizer implements Consumer<OAuth2AuthorizationRequest.Builder> {
    private static Logger logger = LoggerFactory.getLogger(JwtCustomizer.class);

    @Override
    public void accept(OAuth2AuthorizationRequest.Builder builder) {
        if (logger.isTraceEnabled()) {
            logger.trace("Customizing authorization request uri...");
        }
        builder.authorizationUri("http://localhost:9000/api/uaa/oauth2/authorize");
    }
}
