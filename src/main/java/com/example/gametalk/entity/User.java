package com.example.gametalk.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private boolean isDeleted; //탈퇴 여부 (아이디 재사용 방지 & 필요 시 복구 가능)

    protected User() {

    }

    public User(String email, String password, String username, boolean isDeleted) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.isDeleted = isDeleted;
    }
}
