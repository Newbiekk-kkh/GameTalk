package com.example.gametalk.dto.users.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    @NotBlank
    @Email
    private final String username;

    @NotBlank
    private final String password;

    @Builder
    public LoginRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static LoginRequestDto toDto(LoginRequestDto loginRequestDto) {
        return LoginRequestDto.builder()
                .username(loginRequestDto.getUsername())
                .password(loginRequestDto.getPassword())
                .build();
    }
}
