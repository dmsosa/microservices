package com.duvi.authservice.security;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class SecurityTests {

    //todo use webtestclient to expect unauthorized requests
    private WebClient webClient = WebClient.builder().build();


    private static final String ISSUER_URL = "http://localhost:9000/api/uaa";
    private static final String USERNAME = "accountClient";
    private static final String PASSWORD = "account";
    private static final String GRANT_TYPE = "client_credentials";

    @Test
    public void tokenHasCustomAudience() throws Exception {
        String url = ISSUER_URL + "/oauth2/token";
        TokenDTO tokenDTO = webClient.post()
                .uri(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .headers(h -> h.setBasicAuth(USERNAME, PASSWORD))
                .body(BodyInserters.fromFormData("grant_type", GRANT_TYPE))
                .retrieve()
                .bodyToMono(TokenDTO.class).block();
        SignedJWT signedJWT = SignedJWT.parse(tokenDTO.getAccessToken());
        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
        Map<String, Object> claims = claimsSet.getClaims();
        List<String> audience = (List<String>) claims.get("aud");
        assertThat(audience.contains("stats-service")).isTrue();
    }

    static class TokenDTO {
        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("token_type")
        private String tokenType;
        @JsonProperty("expires_in")
        private String expiresIn;
        @JsonProperty("scope")
        private String scope;

        public String getAccessToken() {
            return accessToken;
        }
    }
}
