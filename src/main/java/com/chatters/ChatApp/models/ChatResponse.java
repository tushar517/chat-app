package com.chatters.ChatApp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatResponse {
    String senderUserName;
    String receiverUserName;
    String content;
    ContentType contentType;
}
