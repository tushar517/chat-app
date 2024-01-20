package com.chatters.Chat.App.repository;

import com.chatters.Chat.App.models.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage,String> {
    List<ChatMessage> findByChatId(String s);
}
