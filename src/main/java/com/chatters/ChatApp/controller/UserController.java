package com.chatters.ChatApp.controller;

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
    ){
        userService.connectUser(users);
        return users;
    }

    @PostMapping("/user/createUser")
    public ResponseEntity<Users> registerUser(
        @RequestBody Users user
    ){
        userService.saveUser(user);
        return ResponseEntity.ok(user);
    }

    @MessageMapping("/user/disconnectUser")
    @SendTo("/user/topic")
    @MessageExceptionHandler(MessageConversionException.class)
    public Users disconnect(
            @Payload Users users
    ){
        userService.disconnect(users);
        users.setStatus(false);
        return users;
    }

    @GetMapping("/users")
    @MessageExceptionHandler(MessageConversionException.class)
    public ResponseEntity<List<Users>> findConnectedUsers(){
        List<Users> users = userService.findConnectedUser();
        if(users.isEmpty()){
            return ResponseEntity.ok(new ArrayList<>());
        }else{
            return ResponseEntity.ok(users);
        }

    }

}
