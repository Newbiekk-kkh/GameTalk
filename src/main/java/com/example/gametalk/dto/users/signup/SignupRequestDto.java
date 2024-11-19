package com.example.gametalk.dto.users.signup;

import com.example.gametalk.dto.users.login.LoginRequestDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @NotBlank
    private final String username;

    @NotBlank
    @Email
    private final String email;

    @NotBlank
    private final String password;

    @Builder
    public SignupRequestDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public static SignupRequestDto toDto(SignupRequestDto signupRequestDto) {
        return SignupRequestDto.builder()
                .username(signupRequestDto.getUsername())
                .email(signupRequestDto.getEmail())
                .password(signupRequestDto.getPassword())
                .build();
    }


}
