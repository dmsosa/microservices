package com.duvi.services.account.config.feign.interceptor;

import com.duvi.services.account.client.AuthClient;
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

//I wanted to demonstrate the Factory / Provider pattern here, but since both interceptors
//do the same thing (adding Notification Client's Access Token to the headers),
//it can be redundant in this case, but in the other services only 1 interceptor is used
@Component
public class AuthClientInterceptor implements CustomInterceptor {

    private OAuth2AuthorizedClientManager authorizedClientManager;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public AuthClientInterceptor(OAuth2AuthorizedClientManager authorizedClientManager) {
        this.authorizedClientManager = authorizedClientManager;
    }
    @Override
    public void apply(RequestTemplate requestTemplate) {
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId("accountClient")
                .principal("accountClient")
                .build();
        OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(authorizeRequest);
        OAuth2AccessToken accessToken = Objects.requireNonNull(authorizedClient, "authorizedClient must not be null").getAccessToken();
        OAuth2RefreshToken refreshToken = Objects.requireNonNull(authorizedClient, "authorizedClient must not be null").getRefreshToken();
        if (accessToken.getExpiresAt().isBefore(Instant.now())) {
            logger.info("Refresh Token added to request headers for accountClient / " + LocalTime.now());
            requestTemplate.header("Authorization", "Bearer " + Objects.requireNonNull(refreshToken, "access token is expired and no refresh token was provided").getTokenValue());
        }
        else {
            logger.info("Access Token added to request headers for accountClient / " + LocalTime.now());
            requestTemplate.header("Authorization", "Bearer " + accessToken.getTokenValue());
        }
    }

    @Override
    public Class applyForFeignClientClass() {
        return AuthClient.class;
    }
}
