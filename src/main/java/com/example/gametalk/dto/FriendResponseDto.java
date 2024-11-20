package com.example.gametalk.dto;

import lombok.Getter;

@Getter
public class FriendResponseDto {
    private final String username;

    private final String email;

    public FriendResponseDto(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
