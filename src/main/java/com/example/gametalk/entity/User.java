package com.example.gametalk.entity;

import com.example.gametalk.dto.users.UserResponseDto;
import com.example.gametalk.entity.base.BaseEntity;
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
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    /**
     * @apiNote 탈퇴 여부 (아이디 재사용 방지 & 필요 시 복구 가능)
     */
    @Column(nullable = false)
    private boolean isActivated;

    protected User() {

    }

    public User(String email, String password, String username, boolean isActivated) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.isActivated = isActivated;
    }

    public void patchActivateStatus(boolean activateStatus) {
        this.isActivated = activateStatus;
    }

    public void updateUserInfo(String name, String email, String password) {
        this.username = name;
        this.email = email;
        this.password = password;
    }
      
    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getEmail(),
                user.getUsername()
        );
    }
}
