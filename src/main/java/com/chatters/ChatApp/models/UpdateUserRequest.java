package com.chatters.ChatApp.models;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    String userName;
    String fullName;
    String profileImg;
}
