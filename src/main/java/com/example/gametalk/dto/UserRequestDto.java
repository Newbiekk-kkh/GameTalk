package com.example.gametalk.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserRequestDto {

    //TODO: 검증 기능 추가하기
    private final String email;
    private final String password;
    private final String username;
}
