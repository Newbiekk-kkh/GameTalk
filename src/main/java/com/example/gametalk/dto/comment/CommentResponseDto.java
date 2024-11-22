package com.example.gametalk.dto.comment;

import com.example.gametalk.entity.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CommentResponseDto {

    private final String username;
    private final String comment;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;


    public static CommentResponseDto toDto(Comment comment) {
        return new CommentResponseDto(
                comment.getUser().getUsername(),
                comment.getComment(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
}
