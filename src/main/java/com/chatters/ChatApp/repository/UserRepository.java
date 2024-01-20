package com.chatters.ChatApp.repository;


import com.chatters.ChatApp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {

    List<User> findAllByStatus(boolean status);
}
