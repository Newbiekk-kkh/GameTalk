package com.example.gametalk.dto.posts;

import com.example.gametalk.enums.Genre;
import lombok.Getter;

@Getter
public class PostRequestDto {
    private final String username;
    private final String title;
    private final Genre genre;
    private final String content;

    public PostRequestDto(String username, String title, Genre genre, String content) {
        this.username = username;
        this.title = title;
        this.genre = genre;
        this.content = content;
    }
}
