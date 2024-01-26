package com.chatters.ChatApp.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.id.factory.internal.AutoGenerationTypeStrategy;
import org.springframework.context.annotation.Primary;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class ChatRoom {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    @Column
    private String chatId;
    @Column
    private String senderId;
    @Column
    private String recipientId;
}
