package com.chatters.ChatApp.service;


import com.chatters.ChatApp.models.ChatMessage;
import com.chatters.ChatApp.models.SuccessResponse;
import com.chatters.ChatApp.models.UserAllChats;
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

    public ChatMessage save(ChatMessage chatMessage){
        return repository.save(chatMessage);
    }

    public SuccessResponse<UserAllChats> findChatMessages(
            String chatRoomId
    ){
        return SuccessResponse
                .<UserAllChats>builder()
                .response(new UserAllChats(repository.findByChatRoomId(chatRoomId)))
                .status(true)
                .description("Chats Fetched Successfully")
                .build();

    }
}
