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

    /**
     *
     * @param postId 댓글이 작성된 게시글 Id
     * @param commentId 댓글 Id
     * @return 성공시 "좋아요를 눌렀습니다.", 실패시 "이미 좋아요를 누른 게시물입니다."
     * @throws AuthenticationException
     */
    @PostMapping
    public String likeComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) throws AuthenticationException {
        likeService.validateCommentBelongsToPost(postId, commentId);
        return likeService.likeComment(commentId);
    }

    /**
     *
     * @param postId 댓글이 작성된 게시글 Id
     * @param commentId 댓글 Id
     * @return 성공시 "좋아요가 취소되었습니다.", 실패시 "좋아요를 누르지 않았습니다."
     * @throws AuthenticationException
     */
    @DeleteMapping
    public String cancelLikedComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) throws AuthenticationException {
        likeService.validateCommentBelongsToPost(postId, commentId);
        return likeService.cancelLikedComment(commentId);
    }

    /**
     *
     * @param postId 댓글이 작성된 게시글 Id
     * @param commentId 댓글이 작성된 게시글 Id
     * @return "commentId" : "1" , "numberOfCommentLikes" : "10"
     */
    @GetMapping
    public ResponseEntity<LikeResponseDto> viewNumberOfCommentLikes (@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) {
        likeService.validateCommentBelongsToPost(postId, commentId);
        LikeResponseDto likeResponseDto = likeService.viewNumberOfCommentLikes(commentId);
        return new ResponseEntity<> (likeResponseDto , HttpStatus.OK);
    }
}
