package com.example.gametalk.repository;

import com.example.gametalk.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository <CommentLike, Long> {
    Optional<CommentLike> findByUserAndComment(User user, Comment comment);
    Long countByCommentId(Long id);

    default CommentLike findByUserAndCommentOrElseThrow(User user, Comment comment) {
        return findByUserAndComment(user, comment).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "좋아요를 누르지 않았습니다."));
    }
}
