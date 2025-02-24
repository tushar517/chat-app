package com.chatters.ChatApp.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class SendChatRequest {
    String chatRoomId;
    String senderId;
    String recipientId;
    String content;
}
