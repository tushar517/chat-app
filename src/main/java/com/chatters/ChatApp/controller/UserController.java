package com.chatters.ChatApp.controller;

import com.chatters.ChatApp.models.SuccessResponse;
import com.chatters.ChatApp.models.Users;
import com.chatters.ChatApp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @MessageMapping("/user/addUser") //gifts
    @SendTo("/user/topic") //topic/messages
    @MessageExceptionHandler(MessageConversionException.class)
    public Users connectUser(
            @Payload Users users
    ) {
        Date date = new Date();
        users.setLastSeen(date);
        return userService.connectUser(users);
    }

    @PostMapping("/user/loginUser")
    public ResponseEntity<SuccessResponse> loginUser(
            @RequestBody Users user
    ) {
        return ResponseEntity.ok(
                userService.loginUser(user)
        );
    }

    @PostMapping("/user/createUser")
    public ResponseEntity<SuccessResponse> registerUser(
            @RequestBody Users user
    ) {
        Date date = new Date();
        user.setLastSeen(date);
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @MessageMapping("/user/disconnectUser")
    @SendTo("/user/topic")
    @MessageExceptionHandler(MessageConversionException.class)
    public Users disconnect(
            @Payload Users users
    ) {
        Date date = new Date();
        users.setLastSeen(date);
        return userService.disconnect(users);
    }

    @GetMapping("/users")
    @MessageExceptionHandler(MessageConversionException.class)
    public ResponseEntity<List<Users>> findAllUsers() {
        List<Users> users = userService.findAllUser();
        if (users.isEmpty()) {
            return ResponseEntity.ok(new ArrayList<>());
        } else {
            return ResponseEntity.ok(users);
        }

    }

}
