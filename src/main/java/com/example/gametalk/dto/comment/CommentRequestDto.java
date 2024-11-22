package com.example.gametalk.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


public record CommentRequestDto(@NotBlank String comment) {

    public CommentRequestDto(String comment) {
        this.comment = comment;
    }
}