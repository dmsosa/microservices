package com.duvi.services.noti.config.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalTime;
import java.util.Objects;

@Component
public class FeignInterceptor implements RequestInterceptor {
    private OAuth2AuthorizedClientManager authorizedClientManager;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public FeignInterceptor(OAuth2AuthorizedClientManager authorizedClientManager) {
        this.authorizedClientManager = authorizedClientManager;
    }

    @Override
    public void apply(RequestTemplate template) {
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId("notiClient")
                .principal("notiClient")
                .build();
        OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(authorizeRequest);
        OAuth2AccessToken accessToken = Objects.requireNonNull(authorizedClient, "authorizedClient must not be null").getAccessToken();
        OAuth2RefreshToken refreshToken = Objects.requireNonNull(authorizedClient, "authorizedClient must not be null").getRefreshToken();
        if (accessToken.getExpiresAt().isBefore(Instant.now())) {
            logger.info("Refresh Token added to request headers for notiClient / " + LocalTime.now());
            template.header("Authorization", "Bearer " + Objects.requireNonNull(refreshToken, "Access token is expired and no refresh token was provided!").getTokenValue());
        } else {
            logger.info("Access Token added to request headers for accountClient / " + LocalTime.now());
            template.header("Authorization", "Bearer " + accessToken.getTokenValue());
        }
    }
}
