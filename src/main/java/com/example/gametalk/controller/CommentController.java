package com.example.gametalk.controller;

import com.example.gametalk.dto.comment.CommentRequestDto;
import com.example.gametalk.dto.comment.CommentResponseDto;
import com.example.gametalk.entity.Comment;
import com.example.gametalk.exception.authentication.AuthenticationException;
import com.example.gametalk.service.CommentService;
import com.example.gametalk.utils.SessionUtils;
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
    private final SessionUtils sessionUtils;

    // 댓글 작성
    @PostMapping("/comments")
    public ResponseEntity<String> addComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequestDto dto
    ) throws AuthenticationException {
        CommentResponseDto commentResponseDtoList = commentService.addComment(postId, dto);
        return new ResponseEntity<>("댓글이 작성되었습니다.", HttpStatus.OK);
    }

    // 댓글 조회
    // todo 정렬, 페이지네이션 기능 추가 필요
    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponseDto>> findAllComments(
            @PathVariable Long postId
    ) throws AuthenticationException {
        List<CommentResponseDto> commentResponseDtoList = commentService.findAllComments(postId);
        return new ResponseEntity<>(commentResponseDtoList, HttpStatus.OK);
    }
    
    // 댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequestDto dto
    ) throws AuthenticationException {
        CommentResponseDto commentResponseDto = commentService.updateComment(postId, commentId, dto);
        return new ResponseEntity<>(commentResponseDto, HttpStatus.OK);
    }
    
    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) throws AuthenticationException {
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("댓글이 삭제되었습니다.", HttpStatus.OK);
    }
}
