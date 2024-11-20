package com.example.gametalk.dto.users;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserUpdateResponseDto {

    @NotBlank
    private final String username;

    @NotBlank
    private final String email;

    public UserUpdateResponseDto(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
