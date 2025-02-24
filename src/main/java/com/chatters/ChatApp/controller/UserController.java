package com.chatters.ChatApp.controller;

import com.chatters.ChatApp.enums.UserRole;
import com.chatters.ChatApp.models.*;
import com.chatters.ChatApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/addUser") //use in sendTo in client side
    @SendTo("/topic/connectedUser") //use in subscribing
    public UserResponse connectUser(
            @Payload UserResponse users
    ) {
        return userService.connectUser(users);
    }

    @PostMapping("/auth/user/loginUser")
    public ResponseEntity<SuccessResponse<AuthenticationResponse>> loginUser(
            @RequestBody Users user
    ) {
        SuccessResponse<AuthenticationResponse> response = userService.loginUser(user);
        UserResponse userResponse = response.getResponse().getUserDetail();
        messagingTemplate.convertAndSend(
                "/topic/connectedUser",
                userResponse
        );
        return ResponseEntity.ok(
                response
        );
    }

    @PostMapping("/auth/user/createUser")
    public ResponseEntity<SuccessResponse<AuthenticationResponse>> registerUser(
            @RequestBody UserRequest user
    ) {

        Date date = new Date();
        Users saveUser = Users
                .builder()
                .userRole(UserRole.User)
                .status(false)
                .fullName(user.getFullName())
                .gender(user.getGender())
                .username(user.getUserName())
                .lastSeen(date)
                .password(user.getPassword())
                .profileImg(user.getProfileImg())
                .build();
        SuccessResponse<AuthenticationResponse> response = userService.saveUser(saveUser);
        messagingTemplate.convertAndSend(
                "/topic/connectedUser",
                response.getResponse().getUserDetail()
        );
        return ResponseEntity.ok(response);
    }

    @MessageMapping("/disconnectUser")
    @SendTo("/user/topic")
    public Users disconnect(
            @Payload Users users
    ) {
        Date date = new Date();
        users.setLastSeen(date);
        return userService.disconnect(users);
    }

    @GetMapping("/users")
    @MessageExceptionHandler(MessageConversionException.class)
    public ResponseEntity<SuccessResponse<AllUserResponse>> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUser());

    }

    @PutMapping("/user/updatePassword/{userId}")
    public ResponseEntity<SuccessResponse<AuthenticationResponse>> updatePassword(
            @RequestBody UpdatePasswordRequest request,
            @PathVariable String userId
    ){
        return ResponseEntity.ok(userService.updatePassword(userId,request));
    }

    @PutMapping("user/updateUser/{userId}")
    public ResponseEntity<SuccessResponse<AuthenticationResponse>> updateUser(
            @RequestBody UpdateUserRequest request,
            @PathVariable String userId
    ){
        return ResponseEntity.ok(userService.updateUser(userId,request));
    }
}
