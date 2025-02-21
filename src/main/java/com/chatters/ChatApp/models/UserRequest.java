package com.chatters.ChatApp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String userName;
    private String fullName;
    private String gender;
    private String password;
    private String profileImg;
}
