package com.chatters.ChatApp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table
public class Users {
    @Id
    @Column
    private String userName = "";
    @Column
    private String fullName;
    @Column
    private Boolean status;
    @Column
    private String password;
    @Column
    private String gender;
    @Column
    private Date lastSeen;
}
