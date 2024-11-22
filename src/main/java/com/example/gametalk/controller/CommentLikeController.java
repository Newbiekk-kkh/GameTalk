package com.example.gametalk.controller;

import com.example.gametalk.dto.likes.LikeResponseDto;
import com.example.gametalk.exception.authentication.AuthenticationException;
import com.example.gametalk.service.CommentService;
import com.example.gametalk.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments/{commentId}/likes")
public class CommentLikeController {
    private final LikeService likeService;

    @PostMapping
    public String likeComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) throws AuthenticationException {
        likeService.validateCommentBelongsToPost(postId, commentId);
        return likeService.likeComment(commentId);
    }

    @DeleteMapping
    public String cancelLikedComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) throws AuthenticationException {
        likeService.validateCommentBelongsToPost(postId, commentId);
        return likeService.cancelLikedComment(commentId);
    }

    @GetMapping
    public ResponseEntity<LikeResponseDto> viewNumberOfCommentLikes (@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) {
        likeService.validateCommentBelongsToPost(postId, commentId);
        LikeResponseDto likeResponseDto = likeService.viewNumberOfCommentLikes(commentId);
        return new ResponseEntity<> (likeResponseDto , HttpStatus.OK);
    }
}
