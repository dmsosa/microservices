package com.duvi.gateway.config.security;

import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import java.util.function.Consumer;

public class AuthRequestResolverCustomizer implements Consumer<OAuth2AuthorizationRequest.Builder> {
    @Override
    public void accept(OAuth2AuthorizationRequest.Builder builder) {
        System.out.println("Customizing authorization request uri...");
        builder.authorizationUri("http://localhost:9000/api/uaa/oauth2/authorize");
    }
}
