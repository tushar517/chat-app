package com.chatters.ChatApp.service;

import com.chatters.ChatApp.models.*;
import com.chatters.ChatApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;

    public SuccessResponse<AuthenticationResponse> saveUser(Users user) {
        Optional<Users> storedUser = userRepository.findById(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (storedUser.isPresent()) {
            return SuccessResponse.<AuthenticationResponse>builder()
                    .response(new AuthenticationResponse(
                            "",
                            UserResponse
                                    .builder()
                                    .userName(user.getUsername())
                                    .profileImg(user.getProfileImg())
                                    .fullName(user.getFullName())
                                    .gender(user.getGender())
                                    .lastSeen(new Date())
                                    .build()))
                    .status(false)
                    .description("User Already Exists")
                    .build();
        } else {
            userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return SuccessResponse.<AuthenticationResponse>builder()
                    .response(new AuthenticationResponse(jwtToken,UserResponse
                            .builder()
                            .userName(user.getUsername())
                            .profileImg(user.getProfileImg())
                            .fullName(user.getFullName())
                            .gender(user.getGender())
                            .lastSeen(new Date())
                            .build()))
                    .status(true)
                    .description("Signup successful")
                    .build();
        }
    }

    public Users disconnect(Users users) {
        var storedUser = userRepository.findById(users.getUsername()).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(false);
            userRepository.save(storedUser);
            return storedUser;
        }
        return users;
    }

    public UserResponse connectUser(UserResponse user) {
        try {
            var storedUser = userRepository.findById(user.getUserName().trim()).orElse(null);
            if (storedUser != null) {
                storedUser.setStatus(true);
                userRepository.save(storedUser);
                return new UserResponse(
                        storedUser.getUsername(),
                        storedUser.getFullName(),
                        storedUser.getGender(),
                        storedUser.getLastSeen(),
                        storedUser.getProfileImg()
                );
            }
            return user;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return user;
        }
    }

    public SuccessResponse<AllUserResponse> findAllUser() {
        List<UserResponse> users = userRepository.findAllUsers();

        if (users.isEmpty())
            return SuccessResponse
                    .<AllUserResponse>builder()
                    .description("No user Found")
                    .status(false)
                    .response(new AllUserResponse(new ArrayList<>()))
                    .build();
        else
            return SuccessResponse
                    .<AllUserResponse>builder()
                    .status(true)
                    .description(users.size() + " users found")
                    .response(new AllUserResponse(users))
                    .build();
    }

    public SuccessResponse<AuthenticationResponse> loginUser(Users request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findById(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return SuccessResponse.<AuthenticationResponse>builder()
                .response(new AuthenticationResponse(jwtToken,UserResponse
                        .builder()
                        .userName(user.getUsername())
                        .profileImg(user.getProfileImg())
                        .fullName(user.getFullName())
                        .gender(user.getGender())
                        .lastSeen(new Date())
                        .build()))
                .status(true)
                .description("Login Successful")
                .build();
    }

    public SuccessResponse<AuthenticationResponse> updatePassword(String userId, UpdatePasswordRequest request) {
        Optional<Users> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Users saveUser = user.get();
            if (request.getCurrentPassword().equals(saveUser.getPassword())) {
                saveUser.setPassword(request.getNewPassword());
                userRepository.save(saveUser);
                return SuccessResponse.<AuthenticationResponse>builder()
                        .response(new AuthenticationResponse("",new UserResponse()))
                        .status(true)
                        .description("Password Updated Successfully")
                        .build();
            } else {
                return SuccessResponse.<AuthenticationResponse>builder()
                        .response(new AuthenticationResponse("",new UserResponse()))
                        .status(false)
                        .description("Current password is incorrect")
                        .build();
            }
        } else {
            return SuccessResponse.<AuthenticationResponse>builder()
                    .response(new AuthenticationResponse("",new UserResponse()))
                    .status(false)
                    .description("User Not Found")
                    .build();
        }
    }

    public SuccessResponse<AuthenticationResponse> updateUser(String userId, UpdateUserRequest request) {
        Optional<Users> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Users saveUser = user.get();
            saveUser.setUsername(request.getUserName());
            saveUser.setFullName(request.getFullName());
            saveUser.setProfileImg(request.getProfileImg());
            userRepository.save(saveUser);
            return SuccessResponse.<AuthenticationResponse>builder()
                    .response(new AuthenticationResponse("",UserResponse
                            .builder()
                            .userName(saveUser.getUsername())
                            .profileImg(saveUser.getProfileImg())
                            .fullName(saveUser.getFullName())
                            .gender(saveUser.getGender())
                            .lastSeen(new Date())
                            .build()))
                    .status(true)
                    .description("Details Updated Successfully")
                    .build();

        } else {
            return SuccessResponse.<AuthenticationResponse>builder()
                    .response(new AuthenticationResponse("",new UserResponse()))
                    .status(false)
                    .description("User Not Found")
                    .build();
        }
    }
}
