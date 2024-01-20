package com.chatters.Chat.App.config;

import com.chatters.Chat.App.enums.MessageType;
import com.chatters.Chat.App.models.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageTemplate;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("username");
        if(username!=null){
            log.info("User disconnected: {}",username);
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .build();
            messageTemplate.convertAndSend("/topic/public",chatMessage);
        }
    }
}
