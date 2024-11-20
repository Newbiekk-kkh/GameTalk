package com.example.gametalk.dto.friends;

import com.example.gametalk.entity.Friend;
import lombok.Getter;

@Getter
public class FriendResponseDto {
    private final String username;

    private final String email;

    public FriendResponseDto(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public static FriendResponseDto toDto(Friend friend) {
        return new FriendResponseDto(friend.getReceiver().getUsername(), friend.getReceiver().getEmail());
    }
}
