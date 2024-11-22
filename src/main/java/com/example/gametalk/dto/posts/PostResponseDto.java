package com.example.gametalk.dto.posts;

import com.example.gametalk.entity.Post;
import com.example.gametalk.entity.PostLike;
import com.example.gametalk.enums.Genre;
import com.example.gametalk.repository.PostLikeRepository;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDto {

    private final Long id;
    private final String username;
    private final String title;

    @Setter
    private int likes;

    private final Genre genre;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public PostResponseDto(Long id, String username, String title, Genre genre, String content, int likes, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.genre = genre;
        this.content = content;
        this.likes = likes;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static PostResponseDto toDto(Post post) {

        return new PostResponseDto(
                post.getId(),
                post.getUser().getUsername(),
                post.getTitle(),
                post.getGenre(),
                post.getContent(),
                post.getLikes().size(),
                post.getCreatedAt(),
                post.getModifiedAt()
        );
    }
}
