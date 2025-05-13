package com.crypto.traiding.config;

import com.crypto.traiding.api.KrakenWebSocketClient;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KrakenApiConfig {

    private final KrakenWebSocketClient krakenWebSocketClient;

    public KrakenApiConfig(KrakenWebSocketClient krakenWebSocketClient) {
        this.krakenWebSocketClient = krakenWebSocketClient;
    }

    @PostConstruct
    public void init() {
        krakenWebSocketClient.connect();
    }
}