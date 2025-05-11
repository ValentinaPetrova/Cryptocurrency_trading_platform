package com.crypto.traiding.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.lang.NonNull;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(@NonNull WebSocketHandlerRegistry registry) {
        // You only need this if you're building your own WebSocket server
        // registry.addHandler(yourWebSocketHandler, "/ws").setAllowedOrigins("*");
    }
}
