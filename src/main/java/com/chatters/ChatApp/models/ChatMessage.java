package com.chatters.ChatApp.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class ChatMessage {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    @Column
    private String chatRoomId;
    @Column
    private String senderId;
    @Column
    private String recipientId;
    @Column
    private ContentType contentType;
    @Column
    private String content;
    @Column
    private Date timeStamp;
}
