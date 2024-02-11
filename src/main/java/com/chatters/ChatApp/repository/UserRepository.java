package com.chatters.ChatApp.repository;


import com.chatters.ChatApp.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,String> {

    List<Users> findAllByStatus(boolean status);
}
