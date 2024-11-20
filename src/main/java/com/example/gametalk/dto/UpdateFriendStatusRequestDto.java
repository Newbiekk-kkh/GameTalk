package com.example.gametalk.dto;

import com.example.gametalk.enums.FriendStatus;
import lombok.Getter;

@Getter
public class UpdateFriendStatusRequestDto {
    private final FriendStatus status;
    private final String email;

    public UpdateFriendStatusRequestDto(FriendStatus status, String email) {
        this.status = status;
        this.email = email;
    }
}
