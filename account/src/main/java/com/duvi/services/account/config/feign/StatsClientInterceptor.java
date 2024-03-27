package com.duvi.services.account.config.feign;

import com.duvi.services.account.client.StatClient;
import feign.RequestTemplate;
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
public class StatsClientInterceptor implements CustomInterceptor {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private OAuth2AuthorizedClientManager authorizedClientManager;
    public StatsClientInterceptor(OAuth2AuthorizedClientManager authorizedClientManager) {
        this.authorizedClientManager = authorizedClientManager;
    }
    @Override
    public void apply(RequestTemplate requestTemplate) {
        logger.info("Intercepting Feign request for accountClient / " + LocalTime.now());
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId("accountClient")
                .principal("account")
                .build();
        OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(authorizeRequest);
        OAuth2AccessToken accessToken = Objects.requireNonNull(authorizedClient).getAccessToken();
        OAuth2RefreshToken refreshToken = Objects.requireNonNull(authorizedClient).getRefreshToken();
        if (accessToken.getExpiresAt().isBefore(Instant.now())) {
            logger.info("Refresh Token added to request headers for accountClient / " + LocalTime.now());
            requestTemplate.header("Authorization", "Bearer " + refreshToken.getTokenValue());
        }
        else {
            logger.info("Access Token added to request headers for accountClient / " + LocalTime.now());
            requestTemplate.header("Authorization", "Bearer " + accessToken.getTokenValue());
        }
    }

    @Override
    public Class applyForFeignClientClass() {
        return StatClient.class;
    }
}
