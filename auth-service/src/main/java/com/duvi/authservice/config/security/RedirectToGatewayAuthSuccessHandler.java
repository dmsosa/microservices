package com.duvi.authservice.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RedirectToGatewayAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final Logger logger = LoggerFactory.getLogger(RedirectToGatewayAuthSuccessHandler.class);
    private final String GATEWAY_ON_LOCALHOST_URL = "http://127.0.0.1:8061/index";
    public RedirectToGatewayAuthSuccessHandler() {

    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (logger.isTraceEnabled()) {
            logger.trace("Request successfully authenticated, redirecting to gateway on localhost...");
        }
        response.setStatus(HttpStatus.FOUND.value());
        response.setHeader("Location", GATEWAY_ON_LOCALHOST_URL);
    }
}
