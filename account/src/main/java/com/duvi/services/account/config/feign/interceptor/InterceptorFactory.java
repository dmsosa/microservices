package com.duvi.services.account.config.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
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
        if (logger.isTraceEnabled()) {
            logger.trace("Intercepted feign request, will be handled by client type: " + requestTemplate.feignTarget().type());
        } else {
            logger.trace("Interceptor not found for feign request of client type: " + requestTemplate.feignTarget().type());
        }
        interceptor.apply(requestTemplate);
    }
}
