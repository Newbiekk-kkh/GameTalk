package com.example.gametalk.dto;

import com.example.gametalk.enums.Genre;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostCreateResponseDto {
    private final String title;
    private final Genre genre;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public PostCreateResponseDto(String title, Genre genre, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.title = title;
        this.genre = genre;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
