package com.crypto.traiding.api;

import com.crypto.traiding.api.dto.KrakenTickerResponse;
import com.crypto.traiding.api.store.KrakenPriceStore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.websocket.*;
//import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URI;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@ClientEndpoint
public class KrakenWebSocketClient {

    private static final String KRAKEN_WS_URL = "wss://ws.kraken.com";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Session session;
    private final BlockingQueue<KrakenTickerResponse> responseQueue = new ArrayBlockingQueue<>(1);

    public void connect() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(KRAKEN_WS_URL));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public KrakenTickerResponse getTicker(String pair) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(KRAKEN_WS_URL));

            String subscribeMessage = String.format("""
                {
                  "event": "subscribe",
                  "pair": ["%s"],
                  "subscription": {"name": "ticker"}
                }
                """, pair);

            session.getAsyncRemote().sendText(subscribeMessage);

            // Wait for response with timeout
            return responseQueue.poll(3, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException("WebSocket error: " + e.getMessage(), e);
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        //this.session = session;
        System.out.println("Connected to Kraken WebSocket");

        // Subscribe to ticker info for selected currency pairs
        String subscriptionMessage = "{" +
                "\"event\":\"subscribe\"," +
                "\"pair\":[\"XBT/USD\", \"ETH/USD\", \"LTC/USD\"]," +
                "\"subscription\":{\"name\":\"ticker\"}" +
                "}";

        session.getAsyncRemote().sendText(subscriptionMessage);
    }

    @OnMessage
    public void onMessage(String message) {
        // Ignore non-data messages
        if (message.startsWith("{") || message.contains("heartbeat")) {
            return;
        }

        try {
            JsonNode jsonArray = objectMapper.readTree(message);

            if (jsonArray.isArray() && jsonArray.size() >= 4) {
                String pair = jsonArray.get(3).asText();
                JsonNode priceData = jsonArray.get(1);
                JsonNode askArray = priceData.get("a");

                if (askArray != null && askArray.isArray()) {
                    BigDecimal askPrice = new BigDecimal(askArray.get(0).asText());
                    KrakenPriceStore.updatePrice(pair, askPrice);
                    System.out.println("Updated price for " + pair + ": " + askPrice);
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing Kraken message: " + message);
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("WebSocket error: " + throwable.getMessage());
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("WebSocket closed: " + closeReason);
    }
}
