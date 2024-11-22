package com.example.gametalk.service;

import com.example.gametalk.dto.likes.LikeResponseDto;
import com.example.gametalk.entity.*;
import com.example.gametalk.exception.authentication.AuthenticationException;
import com.example.gametalk.repository.*;
import com.example.gametalk.utils.SessionUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final SessionUtils sessionUtils;

    // 게시글 좋아요
    @Transactional
    public String likePost(Long postId) throws AuthenticationException {
        User loginUser = userRepository.findByEmailOrElseThrow(sessionUtils.getLoginUserEmail());
        Post post = postRepository.findByIdOrElseThrow(postId);

        isMyPost(loginUser, post);

        PostLike likedPost = new PostLike();
        likedPost.setUser(loginUser);
        likedPost.setPost(post);

        isAlreadyLiked(likedPost);

        postLikeRepository.save(likedPost);
        return "좋아요를 눌렀습니다.";
    }

    // 게시글 좋아요 취소
    @Transactional
    public String cancelLikedPost(Long postId) throws AuthenticationException {
        User loginUser = userRepository.findByEmailOrElseThrow(sessionUtils.getLoginUserEmail());
        Post LikedPost = postRepository.findByIdOrElseThrow(postId);

        PostLike findLikedPost = postLikeRepository.findByUserAndPostOrElseThrow(loginUser, LikedPost);

        postLikeRepository.delete(findLikedPost);

        return "좋아요가 취소되었습니다.";
    }

    // 게시글 좋아요 갯수 보기
    public LikeResponseDto viewNumberOfPostLikes(Long postId) {
        Long numberOfPostLikes = postLikeRepository.countByPostId(postId);
        return new LikeResponseDto(postId, numberOfPostLikes);
    }

    // 댓글 좋아요
    @Transactional
    public String likeComment(Long commentId) throws AuthenticationException {
        User loginUser = userRepository.findByEmailOrElseThrow(sessionUtils.getLoginUserEmail());
        Comment comment = commentRepository.findCommentIdOrElseThrow(commentId);

        isMyComment(loginUser, comment);

        CommentLike likedComment = new CommentLike();
        likedComment.setUser(loginUser);
        likedComment.setComment(comment);

        isAlreadyLiked(likedComment);

        commentLikeRepository.save(likedComment);
        return "좋아요를 눌렀습니다.";
    }

    // 댓글 좋아요 취소
    @Transactional
    public String cancelLikedComment(Long commentId) throws AuthenticationException {
        User loginUser = userRepository.findByEmailOrElseThrow(sessionUtils.getLoginUserEmail());
        Comment LikedComment = commentRepository.findCommentIdOrElseThrow(commentId);

        CommentLike findLikedComment = commentLikeRepository.findByUserAndCommentOrElseThrow(loginUser, LikedComment);

        commentLikeRepository.delete(findLikedComment);

        return "좋아요가 취소되었습니다.";
    }

    // 댓글 좋아요 갯수보기
    public LikeResponseDto viewNumberOfCommentLikes(Long commentId) {
        Long numberOfCommentLikes = commentLikeRepository.countByCommentId(commentId);
        return new LikeResponseDto(commentId, numberOfCommentLikes);
    }

    // 이미 게시글 좋아요를 눌렀는지 확인
    public void isAlreadyLiked(PostLike postLike) {
        Optional<PostLike> findLikedPost = postLikeRepository.findByUserAndPost(postLike.getUser(), postLike.getPost());
        if (findLikedPost.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 좋아요를 누른 게시물입니다.");
        }
    }

    // 내 게시물인지 확인
    public void isMyPost(User user, Post post) {
        if (Objects.equals(user.getId(), post.getUser().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "본인의 게시물에는 좋아요를 누를 수 없습니다.");
        }
    }

    // 이미 댓글에 좋아요를 눌렀는지 확인
    public void isAlreadyLiked(CommentLike commentLike) {
        Optional<CommentLike> findLikedComment = commentLikeRepository.findByUserAndComment(commentLike.getUser(), commentLike.getComment());
        if (findLikedComment.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 좋아요를 누른 댓글입니다.");
        }
    }

    // 내 댓글인지 확인
    public void isMyComment(User user, Comment comment) {
        if (Objects.equals(user.getId(), comment.getUser().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "본인의 댓글에는 좋아요를 누를 수 없습니다.");
        }
    }

    // 논리적 검증 : 댓글이 해당 게시물에 속하는지 확인
    public void validateCommentBelongsToPost(Long postId, Long commentId) {
        boolean exists = commentRepository.existsByIdAndPostId(commentId, postId);
        if (!exists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 URL 입니다.");
        }
    }

}
