package com.duvi.gateway.factory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;


@Component
public class DmFilterFactory extends AbstractGatewayFilterFactory<DmFilterFactory.Config> {

    private Logger logger = LoggerFactory.getLogger(DmFilterFactory.class);
    public DmFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            //Pre-processing
            if (config.preLog) {
                ServerHttpRequest request = exchange.getRequest();
                HttpHeaders headers = request.getHeaders();
                if (headers.containsKey("specialKey")) {
                    logger.info("This request is special!");
                }
                logger.info("Pre Logging filter..." + config.getBaseMessage());
            }
            //Post-processing, logging all headers from the response
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                HttpHeaders headers = exchange.getRequest().getHeaders();
                headers.forEach((k, v) -> {
                    System.out.println("Key: '" + k + "' value: '" + v + "' !");
                });
                System.out.println("Post filtered");
            }));
        });
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("baseMessage", "preLog", "postLog");
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Config {
        private String baseMessage;
        private Boolean preLog;
        private Boolean postLog;
    }
}