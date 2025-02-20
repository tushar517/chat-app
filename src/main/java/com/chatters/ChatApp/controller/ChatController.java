package com.chatters.ChatApp.controller;


import com.chatters.ChatApp.models.ChatMessage;
import com.chatters.ChatApp.models.ChatNotification;
import com.chatters.ChatApp.models.SuccessResponse;
import com.chatters.ChatApp.models.UserAllChats;
import com.chatters.ChatApp.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
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

    @MessageMapping("/chat")//use in sendTo in client side
    @MessageExceptionHandler(MessageConversionException.class)
    public ChatMessage sendMessage(
            @Payload ChatMessage chatMessage
    ){
        Date date = new Date();
        try{
            chatMessage.setTimeStamp(date);
            ChatMessage savedMsg = chatMessageService.save(chatMessage);
            messagingTemplate.convertAndSend(
                    "/topic/messages/"+chatMessage.getChatRoomId(),
                    ChatNotification.builder()
                            .id(savedMsg.getId())
                            .senderId(savedMsg.getSenderId())
                            .recipientId(savedMsg.getRecipientId())
                            .content(savedMsg.getContent())
                            .build()
            );

        return chatMessage;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return chatMessage;
        }
    }

    @GetMapping("/messages/{chatRoomId}")
    @MessageExceptionHandler(MessageConversionException.class)
    public ResponseEntity<SuccessResponse<UserAllChats>> findChatMessage(
            @PathVariable("chatRoomId") String chatRoomId
    ){
        return ResponseEntity.ok(
                chatMessageService.findChatMessages(chatRoomId)
        );
    }


}
