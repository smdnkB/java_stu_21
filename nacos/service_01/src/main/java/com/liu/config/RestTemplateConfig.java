package com.liu.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    @LoadBalanced // 会使 RestTemplate 拥有负载均衡功能
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
