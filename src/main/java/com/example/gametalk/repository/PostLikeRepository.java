package com.example.gametalk.repository;

import com.example.gametalk.entity.PostLike;
import com.example.gametalk.entity.Post;
import com.example.gametalk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository <PostLike, Long> {
    Optional<PostLike> findByUserAndPost(User user, Post post);
    Long countByPostId(Long id);

    default PostLike findByUserAndPostOrElseThrow(User user, Post post) {
        return findByUserAndPost(user, post).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "좋아요를 누르지 않았습니다."));
    }
}
