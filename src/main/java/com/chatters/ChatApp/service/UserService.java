package com.chatters.ChatApp.service;

import com.chatters.ChatApp.Excpetion.UserAlreadyExistException;
import com.chatters.ChatApp.models.Users;
import com.chatters.ChatApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void saveUser(Users user) {
        Optional<Users> storedUser = userRepository.findById(user.getUserName());
        if (storedUser.isPresent()) {
            throw new UserAlreadyExistException("Username Already Exists");
        }
        userRepository.save(user);

    }

    public void disconnect(Users users) {
        var storedUser = userRepository.findById(users.getUserName()).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(false);
            userRepository.save(storedUser);
        }
    }

    public void connectUser(Users user) {
        var storedUser = userRepository.findById(user.getUserName()).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(true);
            userRepository.save(storedUser);
        }
    }

    public List<Users> findConnectedUser() {
        return userRepository.findAllByStatus(true);
    }
}
