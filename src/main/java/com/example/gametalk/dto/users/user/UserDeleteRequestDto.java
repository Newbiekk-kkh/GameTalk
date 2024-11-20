package com.example.gametalk.dto.users.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserDeleteRequestDto {

    @NotBlank
    @Email
    private final String email;

    @NotBlank
    private final String password;

    public UserDeleteRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
