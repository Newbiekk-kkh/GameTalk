package com.example.gametalk.dto;

import com.example.gametalk.entity.Post;
import com.example.gametalk.entity.User;
import com.example.gametalk.enums.Genre;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private final String username;
    private final String title;
    private final Genre genre;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public PostResponseDto(String username, String title, Genre genre, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.username = username;
        this.title = title;
        this.genre = genre;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static PostResponseDto toDto(Post post) {
        return new PostResponseDto(
          post.getUser().getUsername(),
          post.getTitle(),
          post.getGenre(),
          post.getContent(),
          post.getCreatedAt(),
          post.getModifiedAt()
        );
    }
}