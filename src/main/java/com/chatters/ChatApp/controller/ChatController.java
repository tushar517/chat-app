package com.chatters.ChatApp.controller;


import com.chatters.ChatApp.models.*;
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
            @Payload SendChatRequest request
    ){
        Date date = new Date();
        try{
            ChatMessage chatMessage = ChatMessage
                    .builder()
                    .timeStamp(date)
                    .contentType(ContentType.Text)
                    .chatRoomId(request.getChatRoomId())
                    .content(request.getContent())
                    .senderId(request.getSenderId())
                    .recipientId(request.getRecipientId())
                    .build();
            chatMessageService.save(chatMessage);
            messagingTemplate.convertAndSend(
                    "/topic/messages/"+chatMessage.getChatRoomId(),
                    chatMessage
            );

        return chatMessage;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return null;
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
