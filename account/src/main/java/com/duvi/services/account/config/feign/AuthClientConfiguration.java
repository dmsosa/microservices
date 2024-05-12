package com.duvi.services.account.config.feign;

import com.duvi.services.account.client.AuthClient;
import feign.Feign;
import feign.Request;
import feign.Target;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthClientConfiguration {
    @Bean
    Feign.Builder builder() {
        int readTimeoutMillis = 10000;
        int connectTimeoutMillis = 5000;
        boolean followRedirects = false;
        return Feign.builder()
                .options(new Request.Options(
                        connectTimeoutMillis,
                        readTimeoutMillis,
                        followRedirects));
    }
}
