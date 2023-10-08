package com.nuria.htmxdemo.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SimpleWebSocketHandler implements WebSocketHandler {

    private final List<WebSocketSession> activeSessions = new CopyOnWriteArrayList<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        activeSessions.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(message.getPayload().toString());
        String country = rootNode.path("country").asText();
        broadcast(country);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("Error de transporte: " + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        activeSessions.remove(session);
    }

    public void broadcast(String message) throws IOException {
        for (WebSocketSession session : activeSessions) {
            if (session.isOpen()) {
                String country = message;
                String responseContent = "<div id=\"notificaciones\" hx-swap-oob=\"beforeend\"><p>Notification: " + country + "</div>";
                session.sendMessage(new TextMessage(responseContent));
            }
        }
    }
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}

