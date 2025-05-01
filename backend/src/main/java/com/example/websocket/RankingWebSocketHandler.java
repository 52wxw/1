package com.example.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class RankingWebSocketHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> sessions = new ArrayList<>();

    // 确保方法签名与 TextWebSocketHandler 中的定义完全一致
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        // 首次连接时发送初始排名
        sendRankings(session, "[{\"username\":\"user1\",\"score\":200,\"solvedTasks\":2}]");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        sessions.remove(session);
    }

    private void sendRankings(WebSocketSession session, String rankingsJson) throws IOException {
        session.sendMessage(new TextMessage(rankingsJson));
    }

    public void broadcastRankings(String rankingsJson) {
        sessions.forEach(session -> {
            try {
                sendRankings(session, rankingsJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
