package com.chatters.ChatApp.controller;

import com.chatters.ChatApp.models.Users;
import com.chatters.ChatApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @MessageMapping("/user.addUser")
    @SendTo("/user/topic")
    public Users addUser(
            @Payload Users users
    ){
        userService.saveUser(users);
        return users;
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/topic")
    public Users disconnect(
            @Payload Users users
    ){
        userService.disconnect(users);
        return users;
    }

    @GetMapping("/users")
    public ResponseEntity<List<Users>> findConnectedUsers(){
        List<Users> users = userService.findConnectedUser();
        if(users.isEmpty()){
            return ResponseEntity.ok(new ArrayList<>());
        }else{
            return ResponseEntity.ok(users);
        }

    }

}
