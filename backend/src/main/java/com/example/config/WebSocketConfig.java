package com.example.config;

import com.example.websocket.RankingWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(rankingWebSocketHandler(), "/ws/ranking")
                .setAllowedOrigins("*");
    }

    @Bean // 显式声明 Bean
    public RankingWebSocketHandler rankingWebSocketHandler() {
        return new RankingWebSocketHandler();
    }
}
