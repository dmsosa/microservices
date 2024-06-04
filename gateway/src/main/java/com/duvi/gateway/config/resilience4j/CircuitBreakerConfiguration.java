package com.duvi.gateway.config.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;

@Configuration
@ConfigurationProperties("gateway.circuit-breaker")
@Slf4j
@Getter
@Setter
public class CircuitBreakerConfiguration {

    private String name;
    private Float failureRateThreshold;
    private Float slowCallRateThreshold;
    private Integer slowCallDurationThresholdMilis;
    private Integer waitDurationInOpenStateMilis;
    private Integer permittedNumberOfCallsInHalfOpenState;
    private Integer slidingWindowSize;
    private Integer timeoutDurationMilis;
    private Integer minimumNumberOfCalls;

    @Bean("account-circuit-breaker")
    public ReactiveCircuitBreaker accountCircuitBreaker(
        ReactiveCircuitBreakerFactory factory,
        CircuitBreakerRegistry registry
    ) {
        return createCircuitBreaker(name, factory, registry);
    }

    private ReactiveCircuitBreaker createCircuitBreaker(String name, ReactiveCircuitBreakerFactory factory, CircuitBreakerRegistry registry) {
        ReactiveCircuitBreaker reactiveCircuitBreaker = factory.create(name);
        addEventToCircuitBreaker(name, registry);
        return reactiveCircuitBreaker;
    }
    private void addEventToCircuitBreaker(String name, CircuitBreakerRegistry circuitBreakerRegistry) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(name);
        circuitBreaker.getEventPublisher().onEvent(e -> log.info(e.toString()));
    }

    @Bean
    public ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory(CircuitBreakerRegistry circuitBreakerRegistry, TimeLimiterRegistry timeLimiterRegistry) {
        return new ReactiveResilience4JCircuitBreakerFactory(circuitBreakerRegistry, timeLimiterRegistry);
    }

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry(io.github.resilience4j.circuitbreaker.CircuitBreakerConfig config) {
        return CircuitBreakerRegistry.of(config);
    }

    @Bean
    io.github.resilience4j.circuitbreaker.CircuitBreakerConfig circuitBreakerConfig() {
        return io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.custom()
                .minimumNumberOfCalls(minimumNumberOfCalls)
                .failureRateThreshold(failureRateThreshold)
                .slowCallRateThreshold(slowCallRateThreshold)
                .slowCallDurationThreshold(Duration.ofMillis(slowCallDurationThresholdMilis))
                .waitDurationInOpenState(Duration.ofMillis(waitDurationInOpenStateMilis))
                .permittedNumberOfCallsInHalfOpenState(permittedNumberOfCallsInHalfOpenState)
                .slidingWindowSize(slidingWindowSize)
                .build();
    }

    @Bean
    @Primary
    TimeLimiterRegistry timeLimiterRegistry(TimeLimiterConfig timeLimiterConfig) {
        return TimeLimiterRegistry.of(timeLimiterConfig);
    }

    @Bean
    TimeLimiterConfig timeLimiterConfig() {
        return TimeLimiterConfig.custom()
                .cancelRunningFuture(true)
                .timeoutDuration(Duration.ofMillis(timeoutDurationMilis))
                .build();
    }
}
