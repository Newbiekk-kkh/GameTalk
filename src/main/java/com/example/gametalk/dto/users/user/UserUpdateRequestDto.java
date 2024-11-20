package com.example.gametalk.dto.users.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {
    @NotBlank
    private final String username;

    @NotBlank
    private final String email;

    @NotBlank
    private final String password;

    public UserUpdateRequestDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
