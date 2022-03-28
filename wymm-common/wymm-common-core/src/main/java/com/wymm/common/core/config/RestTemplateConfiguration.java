package com.wymm.common.core.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration(proxyBeanMethods = false)
public class RestTemplateConfiguration {
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
}
