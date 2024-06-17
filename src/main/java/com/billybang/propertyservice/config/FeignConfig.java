package com.billybang.propertyservice.config;

import com.billybang.propertyservice.interceptor.AuthRequestInterceptor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients
public class FeignConfig {

    @Bean
    public AuthRequestInterceptor authRequestInterceptor() {
        return new AuthRequestInterceptor();
    }
}
