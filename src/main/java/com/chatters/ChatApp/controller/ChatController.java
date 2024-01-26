package com.chatters.ChatApp.controller;


import com.chatters.ChatApp.models.ChatMessage;
import com.chatters.ChatApp.models.ChatNotification;
import com.chatters.ChatApp.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    @SendTo("/user/messages")
    @MessageExceptionHandler(MessageConversionException.class)
    public ChatMessage processMessage(
            @Payload ChatMessage chatMessage
    ){
        ChatMessage message = new ChatMessage();
        Date date = new Date();
        message.setContent(chatMessage.getContent());
        message.setChatId(chatMessage.getChatId());
        message.setSenderId(chatMessage.getSenderId());
        message.setTimeStamp(date);
        message.setSenderId(chatMessage.getSenderId());
        ChatMessage savedMsg = chatMessageService.save(message);
        chatMessage.setId(savedMsg.getId());
        chatMessage.setTimeStamp(date);

            messagingTemplate.convertAndSendToUser(
                    chatMessage.getRecipientId(),
                    "/queue/messages",
                    ChatNotification.builder()
                            .id(savedMsg.getId())
                            .senderId(savedMsg.getSenderId())
                            .recipientId(savedMsg.getRecipientId())
                            .content(savedMsg.getContent())
                            .build()
            );

        return chatMessage;
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    @MessageExceptionHandler(MessageConversionException.class)
    public ResponseEntity<List<ChatMessage>> findChatMessage(
            @PathVariable("senderId") String senderId,
            @PathVariable("recipientId") String recipientId
    ){
        return ResponseEntity.ok(
                chatMessageService.findChatMessages(senderId,recipientId)
        );
    }


}
