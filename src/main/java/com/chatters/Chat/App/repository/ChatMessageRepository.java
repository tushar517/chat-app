package com.chatters.Chat.App.repository;

import com.chatters.Chat.App.models.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,String> {
    List<ChatMessage> findByChatId(String s);
}
