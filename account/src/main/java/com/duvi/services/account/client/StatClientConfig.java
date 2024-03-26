package com.duvi.services.account.client;

import feign.Contract;
import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
public class StatClientConfig {

    private final static Logger logger = LoggerFactory.getLogger(StatClientConfig.class);
    private OAuth2AuthorizedClientManager authorizedClientManager;
    public StatClientConfig(OAuth2AuthorizedClientManager authorizedClientManager) {
        this.authorizedClientManager = authorizedClientManager;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        logger.info("Intercepting Feign request for accountClient / " + LocalTime.now());
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId("accountClient")
                .principal("account")
                .build();
        OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(authorizeRequest);
        OAuth2AccessToken accessToken = Objects.requireNonNull(authorizedClient).getAccessToken();
        logger.info("Access Token added to request headers for accountClient / " + LocalTime.now());
        return requestTemplate -> requestTemplate.header("Authorization", "Bearer " + accessToken.getTokenValue());
    }

}
