package com.example.gametalk.dto.users.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponseDto {

    @NotBlank
    private final String username;

    @NotBlank
    private final String email;

    @NotBlank
    private final String password;

    @Builder
    public UserResponseDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // 프로필 수정
    public static UserResponseDto toDtoModified(UserResponseDto userResponseDto) {
        return UserResponseDto.builder()
                .username(userResponseDto.getUsername())
                .email(userResponseDto.getEmail())
                .password(userResponseDto.getPassword())
                .build();
    }
}
