package com.example.gametalk.dto.friends;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class FriendRequestDto {
    private final String email;

    @JsonCreator
    public FriendRequestDto(@JsonProperty("email") String email) {
        this.email = email;
    }
}
