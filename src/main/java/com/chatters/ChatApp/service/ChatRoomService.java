package com.chatters.ChatApp.service;

import com.chatters.ChatApp.models.ChatRoom;
import com.chatters.ChatApp.models.ChatRoomIdResponse;
import com.chatters.ChatApp.models.SuccessResponse;
import com.chatters.ChatApp.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    public SuccessResponse<ChatRoomIdResponse> getChatRoomID(
            String senderId,
            String recipientId
    ) {
        val chatRoom =  chatRoomRepository.findBySenderIdAndRecipientId(senderId,recipientId);
        if(chatRoom.isPresent()){
            return SuccessResponse
                    .<ChatRoomIdResponse>builder()
                    .status(true)
                    .description("Chat Room id generated Successfully")
                    .response(new ChatRoomIdResponse(chatRoom.get().getChatRoomId()))
                    .build();
        }else{
            try {
                return SuccessResponse
                        .<ChatRoomIdResponse>builder()
                        .status(true)
                        .description("Chat Room id generated Successfully")
                        .response(new ChatRoomIdResponse(createChatRoomId(senderId,recipientId)))
                        .build();

            } catch (NoSuchAlgorithmException e) {
                return SuccessResponse
                        .<ChatRoomIdResponse>builder()
                        .status(false)
                        .description(e.getMessage())
                        .response(new ChatRoomIdResponse(""))
                        .build();
            }
        }
    }

    private String createChatRoomId(String senderId, String recipientId) throws NoSuchAlgorithmException {
        String chatString = senderId+recipientId;

        // Hash the concatenated string using SHA-256
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] hashBytes = digest.digest(chatString.getBytes());

        // Convert the byte array to a hexadecimal string
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }
        String chatRoomId = hexString.toString();
        ChatRoom senderRecipient = ChatRoom.builder()
                .chatRoomId(chatRoomId)
                .senderId(senderId)
                .recipientId(recipientId)
                .build();

        ChatRoom recipientSender = ChatRoom.builder()
                .chatRoomId(chatRoomId)
                .senderId(recipientId)
                .recipientId(senderId)
                .build();
        chatRoomRepository.save(senderRecipient);
        chatRoomRepository.save(recipientSender);
        return chatRoomId;
    }


}
