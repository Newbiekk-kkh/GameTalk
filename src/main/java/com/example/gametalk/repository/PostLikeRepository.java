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
    // User와 Post로 게시글 좋아요 찾기
    Optional<PostLike> findByUserAndPost(User user, Post post);
    // 특정 게시글Id의 좋아요 갯수 세기
    Long countByPostId(Long id);
    // User와 Post로 게시글 좋아요 찾기, 존재 안할시 예외 처리
    default PostLike findByUserAndPostOrElseThrow(User user, Post post) {
        return findByUserAndPost(user, post).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "좋아요를 누르지 않았습니다."));
    }
}
