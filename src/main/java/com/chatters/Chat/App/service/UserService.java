package com.chatters.Chat.App.service;

import com.chatters.Chat.App.models.User;
import com.chatters.Chat.App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public void saveUser(User user){
        user.setStatus(true);
        userRepository.save(user);
    }

    public void disconnect(User user){
        var storedUser = userRepository.findById(user.getNickName()).orElse(null);
        if(storedUser !=null){
            storedUser.setStatus(false);
            userRepository.save(storedUser);
        }

    }

    public List<User> findConnectedUser(){
        return userRepository.findAllByStatus(true);
    }
}
