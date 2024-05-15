package com.duvi.gateway.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import reactor.core.publisher.Mono;

import java.net.URI;

public class RedirectToIndexAuthenticationSuccessHandler  implements ServerAuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();
    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        if (logger.isTraceEnabled()) {
            logger.trace("Authentication successful!, redirecting to /index by default...");
        };
        return redirectStrategy.sendRedirect(webFilterExchange.getExchange(), URI.create("/index"));
    }
}
