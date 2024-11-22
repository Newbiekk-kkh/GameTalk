package com.example.gametalk.repository;

import com.example.gametalk.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository <CommentLike, Long> {
    // User 와 Comment 로 좋아요 찾기
    Optional<CommentLike> findByUserAndComment(User user, Comment comment);
    // 특정 CommentId의 좋아요 갯수 세기
    Long countByCommentId(Long id);

    // User 와 Comment 로 좋아요 찾기 및 없을시 예외 처리
    default CommentLike findByUserAndCommentOrElseThrow(User user, Comment comment) {
        return findByUserAndComment(user, comment).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "좋아요를 누르지 않았습니다."));
    }
}
