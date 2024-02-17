package com.chatters.ChatApp.service;

import com.chatters.ChatApp.models.SuccessResponse;
import com.chatters.ChatApp.models.Users;
import com.chatters.ChatApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public SuccessResponse saveUser(Users user) {
        Optional<Users> storedUser = userRepository.findById(user.getUserName());
        if (storedUser.isPresent()) {
            return new SuccessResponse(
                    false,
                    "User already Exists"
            );
        }else {
            userRepository.save(user);
            return new SuccessResponse(
                    true,
                    "Signup successful"
            );
        }

    }

    public void disconnect(Users users) {
        var storedUser = userRepository.findById(users.getUserName()).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(false);
            userRepository.save(storedUser);
        }
    }

    public Users connectUser(Users user) {
        var storedUser = userRepository.findById(user.getUserName()).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(true);
            userRepository.save(storedUser);
            return storedUser;
        }
        return user;
    }

    public List<Users> findAllUser() {
        return userRepository.findAll();
    }

    public SuccessResponse loginUser(Users user) {
        Optional<Users> storedUser = userRepository.findById(user.getUserName());
        if (storedUser.isPresent()) {
            if(storedUser.get().getPassword().equals(user.getPassword())) {
                return new SuccessResponse(
                        true,
                        "Login Successful"
                );
            }else{
                return new SuccessResponse(
                        false,
                        "Wrong Password"
                );
            }
        }else{
            return new SuccessResponse(
                    false,
                    "Invalid username"
            );
        }
    }
}
