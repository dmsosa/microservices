package com.duvi.services.account.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Interceptor implements RequestInterceptor {

    private Logger logger = LoggerFactory.getLogger(Interceptor.class);
    @Override
    public void apply(RequestTemplate requestTemplate) {
        logger.info("INTERCEPTED REQUEST ");
    }
}
