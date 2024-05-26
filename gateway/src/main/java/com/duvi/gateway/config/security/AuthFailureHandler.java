package com.duvi.gateway.config.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import reactor.core.publisher.Mono;

import java.net.URI;

public class AuthFailureHandler implements ServerAuthenticationFailureHandler {
    public AuthFailureHandler() {
    }
    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        if (exception.getMessage() == "authorization_request_not_found") {
            SecurityContextHolder.clearContext();
        }
        ServerHttpResponse webResponse = webFilterExchange.getExchange().getResponse();
        webResponse.setStatusCode(HttpStatus.FOUND);
        webResponse.getHeaders().setLocation(URI.create("/index"));

        return Mono.empty();
    }
}
