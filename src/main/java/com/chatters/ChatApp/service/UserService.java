package com.chatters.ChatApp.service;

import com.chatters.ChatApp.models.Users;
import com.chatters.ChatApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public void saveUser(Users user){
        user.setStatus(true);
        userRepository.save(user);
    }

    public void disconnect(Users users){
        var storedUser = userRepository.findById(users.getNickName()).orElse(null);
        if(storedUser !=null){
            storedUser.setStatus(false);
            userRepository.save(storedUser);
        }

    }

    public List<Users> findConnectedUser(){
        return userRepository.findAllByStatus(true);
    }
}
