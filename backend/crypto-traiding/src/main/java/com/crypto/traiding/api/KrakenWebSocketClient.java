package com.crypto.traiding.api;

import com.crypto.traiding.api.store.KrakenPriceStore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import java.math.BigDecimal;
import java.net.URI;

@Component
@ClientEndpoint
public class KrakenWebSocketClient {

    private static final String KRAKEN_WS_URL = "wss://ws.kraken.com";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Session session;

    private static final String[] PAIRS = {
        "XBT/USD", "ETH/USD", "LTC/USD", "ADA/USD", "DOT/USD", "XRP/USD", "SOL/USD", "AVAX/USD", "MATIC/USD", "LINK/USD",
        "BCH/USD", "XLM/USD", "ATOM/USD", "UNI/USD", "DOGE/USD", "SHIB/USD", "TRX/USD", "ALGO/USD", "ETC/USD", "EOS/USD"
    };

    public void connect() {
        if (session == null || !session.isOpen()) {
            try {
                WebSocketContainer container = ContainerProvider.getWebSocketContainer();
                container.connectToServer(this, new URI(KRAKEN_WS_URL));
            } catch (Exception e) {
                System.err.println("Failed to connect to Kraken WebSocket: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("Connected to Kraken WebSocket");

        // Build JSON array of pairs
        StringBuilder pairsJson = new StringBuilder("[");
        for (int i = 0; i < PAIRS.length; i++) {
            pairsJson.append("\"").append(PAIRS[i]).append("\"");
            if (i < PAIRS.length - 1) {
                pairsJson.append(",");
            }
        }
        pairsJson.append("]");

        String subscriptionMessage = String.format("""
            {
              "event": "subscribe",
              "pair": %s,
              "subscription": {"name": "ticker"}
            }
            """, pairsJson);

        session.getAsyncRemote().sendText(subscriptionMessage);
    }

    @OnMessage
    public void onMessage(String message) {
        // Ignore non-data or heartbeat messages
        if (message.startsWith("{") || message.contains("heartbeat")) return;

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
            System.err.println("Error parsing message: " + message);
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
