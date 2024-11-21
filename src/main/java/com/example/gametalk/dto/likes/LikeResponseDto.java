package com.example.gametalk.dto.likes;

import lombok.Getter;

@Getter
public class LikeResponseDto {
    private final Long id;
    private final Long numberOfLikes;

    public LikeResponseDto(Long id, Long numberOfLikes) {
        this.id = id;
        this.numberOfLikes = numberOfLikes;
    }
}
