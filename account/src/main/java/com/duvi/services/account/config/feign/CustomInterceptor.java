package com.duvi.services.account.config.feign;

import feign.RequestTemplate;

public interface CustomInterceptor {
    public void apply(RequestTemplate requestTemplate);
    public Class applyForFeignClientClass();
}
