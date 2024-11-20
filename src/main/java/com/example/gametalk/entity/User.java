package com.example.gametalk.entity;

import com.example.gametalk.dto.users.UserProfileResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;

    }

    public static UserProfileResponseDto toDto(User user) {
        return new UserProfileResponseDto(
                user.getUsername(),
                user.getEmail()
        );
    }
}
