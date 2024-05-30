package com.duvi.services.account.config.feign.interceptor;

import feign.RequestTemplate;

public interface CustomInterceptor {
    public void apply(RequestTemplate requestTemplate);
    public Class applyForFeignClientClass();
}
