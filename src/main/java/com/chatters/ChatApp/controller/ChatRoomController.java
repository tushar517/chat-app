package com.chatters.ChatApp.controller;

import com.chatters.ChatApp.models.ChatRoomIdResponse;
import com.chatters.ChatApp.models.ChatRoomRequest;
import com.chatters.ChatApp.models.SuccessResponse;
import com.chatters.ChatApp.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @PostMapping("/chatRoom/getRoomId")
    public ResponseEntity<SuccessResponse<ChatRoomIdResponse>> getChatRoomId(
            @RequestBody ChatRoomRequest chatRoomRequest
            ){
        return ResponseEntity.ok(chatRoomService.getChatRoomID(chatRoomRequest.getSenderId(),chatRoomRequest.getReceiverId()));
    }
}
