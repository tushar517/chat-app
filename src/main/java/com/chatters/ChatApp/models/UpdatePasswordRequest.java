package com.chatters.ChatApp.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePasswordRequest {
    String currentPassword;
    String newPassword;
}
