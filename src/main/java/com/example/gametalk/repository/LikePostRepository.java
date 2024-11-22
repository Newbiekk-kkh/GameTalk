package com.example.gametalk.repository;

import com.example.gametalk.entity.LikePost;
import com.example.gametalk.entity.Post;
import com.example.gametalk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Repository
public interface LikePostRepository extends JpaRepository <LikePost, Long> {
    Optional<LikePost> findByUserAndPost(User user, Post post);
    Long countByPostId(Long id);

    default LikePost findByUserAndPostOrElseThrow(User user, Post post) {
        return findByUserAndPost(user, post).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "좋아요를 누르지 않았습니다."));
    }
}
