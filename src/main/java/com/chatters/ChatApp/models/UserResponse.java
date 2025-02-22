package com.chatters.ChatApp.models;

import lombok.*;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    String userName;
    String fullName;
    String gender;
    Date lastSeen;
    String profileImg;
}
