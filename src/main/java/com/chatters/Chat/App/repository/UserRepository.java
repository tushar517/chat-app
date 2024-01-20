package com.chatters.Chat.App.repository;

import com.chatters.Chat.App.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User,String> {

    List<User> findAllByStatus(boolean status);
}
