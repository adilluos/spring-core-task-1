package com.adilzhan.firsttask.config;

import com.adilzhan.firsttask.service.web.security.ServiceTokenProvider;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignAuthConfig {
    @Bean
    public RequestInterceptor serviceAuthInterceptor(ServiceTokenProvider tokenProvider) {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                String token = tokenProvider.newToken();
                template.header("Authorization", "Bearer " + token);
            }
        };
    }
}
