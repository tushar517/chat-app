package com.chatters.ChatApp.service;


import com.chatters.ChatApp.models.ChatMessage;
import com.chatters.ChatApp.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository repository;
    private final ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage){
        var chatId = chatRoomService.getChatRoomID(
                chatMessage.getSenderId(),
                chatMessage.getRecipientId(),
                true
        ).orElseThrow();
        chatMessage.setChatId(chatId);
        return repository.save(chatMessage);
    }

    public List<ChatMessage> findChatMessages(
            String senderId, String recipientId
    ){
        var chatID = chatRoomService.getChatRoomID(
                senderId,
                recipientId,
                false
        );
        return chatID.map(repository::findByChatId).orElse(new ArrayList<>());
    }
}
