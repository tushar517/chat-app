package com.chatters.ChatApp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class Users {
    @Id
    @Column
    private String nickName;
    @Column
    private String fullName;
    @Column
    private Boolean status;

}
