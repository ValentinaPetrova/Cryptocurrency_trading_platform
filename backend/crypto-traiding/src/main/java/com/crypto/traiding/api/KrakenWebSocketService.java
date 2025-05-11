package com.crypto.traiding.api;

import com.crypto.traiding.api.dto.KrakenTickerResponse;
import org.springframework.stereotype.Service;

@Service
public class KrakenWebSocketService {

    private final KrakenWebSocketClient krakenWebSocketClient;

    public KrakenWebSocketService(KrakenWebSocketClient krakenWebSocketClient) {
        this.krakenWebSocketClient = krakenWebSocketClient;
    }

    public KrakenTickerResponse getTicker(String pair) {
        return krakenWebSocketClient.getTicker(pair);
    }
}
