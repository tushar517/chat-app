package com.chatters.Chat.App.repository;

import com.chatters.Chat.App.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {

    List<User> findAllByStatus(boolean status);
}
