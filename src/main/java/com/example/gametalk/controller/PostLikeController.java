package com.example.gametalk.controller;

import com.example.gametalk.dto.likes.LikeResponseDto;
import com.example.gametalk.exception.authentication.AuthenticationException;
import com.example.gametalk.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/likes")
public class PostLikeController {
    private final LikeService likeService;

    @PostMapping
    public String likePost(@PathVariable Long postId) throws AuthenticationException {
        return likeService.likePost(postId);
    }

    @DeleteMapping
    public String cancelLikedPost(@PathVariable Long postId) throws AuthenticationException {
        return likeService.cancelLikedPost(postId);
    }

    @GetMapping
    public ResponseEntity<LikeResponseDto> viewNumberOfPostLikes (@PathVariable Long postId) {
        LikeResponseDto likeResponseDto = likeService.viewNumberOfPostLikes(postId);
        return new ResponseEntity<> (likeResponseDto ,HttpStatus.OK);
    }
}
