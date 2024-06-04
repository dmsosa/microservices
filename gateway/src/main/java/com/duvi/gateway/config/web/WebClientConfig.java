package com.duvi.gateway.config.web;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    public WebClientConfig() {

    }
    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder(ReactiveClientRegistrationRepository repository, ReactiveOAuth2AuthorizedClientService service) {
        AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager manager = new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(repository, service);
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauthFilterFunction = new ServerOAuth2AuthorizedClientExchangeFilterFunction(manager);
        String clientId = "gatewayClient";
        oauthFilterFunction.setDefaultClientRegistrationId(clientId);
        return WebClient.builder().baseUrl("http://gateway:8061").filter(oauthFilterFunction);
    }
}
