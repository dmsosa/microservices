package com.duvi.services.account.config.feign;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InterceptorProvider {
    private final List<CustomInterceptor> interceptors;
    public CustomInterceptor getInterceptorOfClientType(Class clazz) {
        List<CustomInterceptor> interceptorList = interceptors.stream().filter(interceptor ->
                        clazz.equals(interceptor.applyForFeignClientClass())
                )
                .toList();
        if (interceptorList.isEmpty()) { return null; }
        return interceptorList.getFirst();
    }
}
