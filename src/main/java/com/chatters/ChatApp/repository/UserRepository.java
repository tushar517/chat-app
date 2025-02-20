package com.chatters.ChatApp.repository;


import com.chatters.ChatApp.models.UserResponse;
import com.chatters.ChatApp.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<Users,String> {

    List<Users> findAllByStatus(boolean status);

    @Query("SELECT new com.chatters.ChatApp.models.UserResponse(user.username,user.fullName,user.gender,user.lastSeen,user.profileImg) from Users user")
    List<UserResponse> findAllUsers();
}
