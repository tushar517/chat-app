package com.chatters.Chat.App.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "chat-message")
public class ChatMessage {

    @Id
    private String id;
    @Column
    private String chatId;
    @Column
    private String senderId;
    @Column
    private String recipientId;
    @Column
    private String content;
    @Column
    private Date timeStamp;
}
