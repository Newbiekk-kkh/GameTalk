package com.example.gametalk.dto;

import com.example.gametalk.enums.FriendStatus;
import lombok.Getter;

@Getter
public class FriendStatusDto {
    private final FriendStatus status;

    private final long receiverId;

    private final long senderId;

    public FriendStatusDto(FriendStatus status, long receiverId, long senderId) {
        this.status = status;
        this.receiverId = receiverId;
        this.senderId = senderId;
    }
}
