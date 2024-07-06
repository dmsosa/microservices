package com.duvi.services.account.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class OAuth2Config {
    @Autowired
    private DiscoveryClient discoveryClient;


    public Map<String, String> findAuthServiceUris() {
        //finding auth instances
        ServiceInstance authInstance = discoveryClient.getInstances("auth-service").getFirst();
        String issuerUri = authInstance.getUri().toString();
        Map<String, String> map = new HashMap<>();
        map.put("host", issuerUri);
        map.put("issuerUri", issuerUri + "/api/uaa");
        map.put("tokenUri", issuerUri + "/api/uaa/oauth2/token");
        map.put("authorizationUri", issuerUri + "/api/uaa/oauth2/authorize");
        return map;
    }

    @Bean
    public ClientRegistration clientRegistration() {
        Map<String, String> authUris = findAuthServiceUris();

        return ClientRegistration.withRegistrationId("accountClient")
                .clientId("accountClient")
                .clientSecret("account")
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .scope(List.of("openid", "profile"))
                .clientName("accountService")
                .issuerUri(authUris.get("issuerUri"))
                .tokenUri(authUris.get("tokenUri"))
                .authorizationUri(authUris.get("authorizationUri"))
                .build();
    }



    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(clientRegistration());
    }
}
