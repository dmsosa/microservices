package com.duvi.services.account.config.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class InterceptorFactory implements RequestInterceptor {

    private Logger logger = LoggerFactory.getLogger(InterceptorFactory.class);
    private final InterceptorProvider interceptorProvider;
    public InterceptorFactory(InterceptorProvider interceptorProvider) {
        this.interceptorProvider = interceptorProvider;
    }
    @Override
    public void apply(RequestTemplate requestTemplate) {
        CustomInterceptor interceptor = interceptorProvider.getInterceptorOfClientType(requestTemplate.feignTarget().type());
        if (interceptor != null) {
            logger.info("INTERCEPTED REQUEST, WILL BE HANDLED BY CLIENT TYPE: " + requestTemplate.feignTarget().type());
            interceptor.apply(requestTemplate);
        }
        logger.info("INTERCEPTED REQUEST" );

    }
}
