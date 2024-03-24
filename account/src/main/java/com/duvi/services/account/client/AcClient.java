package com.duvi.services.account.client;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AcClient {

    @Bean
    @LoadBalanced
    RestTemplate template() {
        return new RestTemplate();
    }

    @Bean
    WebClient clientBuilder(OAuth2AuthorizedClientManager clientManager) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2Client = new ServletOAuth2AuthorizedClientExchangeFilterFunction(clientManager);
        oauth2Client.setDefaultOAuth2AuthorizedClient(true);
        return WebClient.builder().baseUrl("http://localhost:8085/api/stats")
                .apply(oauth2Client.oauth2Configuration()).build();
    }

    @Bean
    OAuth2AuthorizedClientManager clientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository
    ) {
        OAuth2AuthorizedClientProvider provider = OAuth2AuthorizedClientProviderBuilder.builder()
                .clientCredentials()
                .build();

        DefaultOAuth2AuthorizedClientManager authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(
                clientRegistrationRepository, authorizedClientRepository
                );
        authorizedClientManager.setAuthorizedClientProvider(provider);
        return  authorizedClientManager;
    }
}
