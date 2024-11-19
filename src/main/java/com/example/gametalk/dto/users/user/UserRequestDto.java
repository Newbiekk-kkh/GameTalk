package com.example.gametalk.dto.users.user;

import com.example.gametalk.dto.users.signup.SignupRequestDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserRequestDto {

    @NotBlank
    private final String username;

    @NotBlank
    private final String email;

    @NotBlank
    private final String password;

    @Builder
    public UserRequestDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // 프로필 수정
    public static UserRequestDto toDtoModified(UserRequestDto userRequestDto) {
        return UserRequestDto.builder()
                .username(userRequestDto.getUsername())
                .email(userRequestDto.getEmail())
                .password(userRequestDto.getPassword())
                .build();
    }

    // 회원 탈퇴
    public static UserRequestDto toDtoDeleted(UserRequestDto userRequestDto) {
        return UserRequestDto.builder()
                .email(userRequestDto.getEmail())
                .password(userRequestDto.getPassword())
                .build();
    }
}
