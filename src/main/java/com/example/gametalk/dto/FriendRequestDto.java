package com.example.gametalk.dto;

import lombok.Getter;

@Getter
public class FriendRequestDto {
    private final String email;

    public FriendRequestDto(String email) {
        this.email = email;
    }
}
