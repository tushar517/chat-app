package com.chatters.Chat.App.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "chat-room")
public class ChatRoom {

    @Id
    private String id;
    @Column
    private String chatId;
    @Column
    private String senderId;
    @Column
    private String recipientId;
}
