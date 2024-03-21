package com.duvi.services.account.client;

import feign.RequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Configuration
public class StatClientConfig {

    private final Logger logger = LoggerFactory.getLogger(StatClientConfig.class);
    private ClientRegistrationRepository repository;
    private OAuth2AuthorizedClientService clientService;

    public StatClientConfig(ClientRegistrationRepository repository, OAuth2AuthorizedClientService clientService) {
        this.repository = repository;
        this.clientService = clientService;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        ClientRegistration clientRegistration = repository.findByRegistrationId("account");
        logger.info("Intercepting feign request, attaching tokens to registered client: " + clientRegistration.getClientId() + " !!");
        return requestTemplate -> requestTemplate.header("Authorization", "Bearer "+ "token");
    }

}
