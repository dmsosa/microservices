package com.duvi.gateway.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
public class DmGlobalFilter implements GlobalFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("Global Processing request at " + LocalDateTime.now());
        ServerHttpRequest request = exchange.getRequest().mutate().header("Global-Processed", "true").build();
        return chain.filter(exchange.mutate().request(request).build());
    }
}
