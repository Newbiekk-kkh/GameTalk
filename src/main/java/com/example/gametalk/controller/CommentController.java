package com.example.gametalk.controller;

import com.example.gametalk.dto.comment.CommentRequestDto;
import com.example.gametalk.dto.comment.CommentResponseDto;
import com.example.gametalk.entity.Comment;
import com.example.gametalk.exception.authentication.AuthenticationException;
import com.example.gametalk.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}")
public class CommentController {

    private final CommentService commentService;

    // todo 댓글 작성
    @PostMapping("/comments")
    public ResponseEntity<String> addComment(
            @PathVariable String postId,
            @Valid @RequestBody Comment comment
    ) throws AuthenticationException {
        CommentResponseDto commentResponseDto = CommentService.add(postId);
        return new ResponseEntity<>("댓글이 작성되었습니다.", HttpStatus.OK);
    }

    // todo 댓글 조회
    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments(
            @PathVariable String postId
    ) throws AuthenticationException {
        List<CommentResponseDto> commentResponseDtoList = CommentService.findAll(postId);
        return new ResponseEntity<>(commentResponseDtoList, HttpStatus.OK);
    }
    
    // todo 댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable String postId,
            @PathVariable String commentId,
            @Valid @RequestBody CommentRequestDto dto
    ) throws AuthenticationException {
        CommentResponseDto commentResponseDto = CommentService.update(postId, commentId, dto.getComment());
        return new ResponseEntity<>(commentResponseDto, HttpStatus.OK);
    }
    
    // todo 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable String postId,
            @PathVariable String commentId
    ) throws AuthenticationException {
        commentService.delete(postId);
        return new ResponseEntity<>("댓글이 삭제되었습니다.", HttpStatus.OK);
    }
}
