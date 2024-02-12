package com.chatters.ChatApp.Excpetion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserDoesNotExist extends RuntimeException{
    public UserDoesNotExist(String message){super(message);}
}
