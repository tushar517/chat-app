package com.chatters.ChatApp.repository;


import com.chatters.ChatApp.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<Users,String> {

    List<Users> findAllByStatus(boolean status);
}
