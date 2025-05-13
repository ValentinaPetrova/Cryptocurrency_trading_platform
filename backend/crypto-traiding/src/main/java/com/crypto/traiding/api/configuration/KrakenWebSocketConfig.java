package com.crypto.traiding.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class KrakenWebSocketConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
