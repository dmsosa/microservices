package com.duvi.gateway.config;

import com.duvi.gateway.factory.DmFilterFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Collections;
import java.util.List;

@Configuration
@Profile("alternative")
public class GatewayConfig {

    @Value("${microservices.baseMessage}")
    private String baseMessage;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder rlb, DmFilterFactory filterFactory) {
        return rlb.routes()
                .route("account", p ->
                        p.path("/account/**")
                        .filters(f -> f.prefixPath("/api")
                        .filter(filterFactory.apply(new DmFilterFactory.Config(baseMessage, true, true))))
                        .uri("lb://account"))
                .route("stats", p ->
                        p.path("/stats/**")
                        .filters(f ->
                        f.prefixPath("/api")
                        .filter(filterFactory.apply(new DmFilterFactory.Config(baseMessage, false, true))))
                        .uri("lb://account"))
                .build();
    }
}
