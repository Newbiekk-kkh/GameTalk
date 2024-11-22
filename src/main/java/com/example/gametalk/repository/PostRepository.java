package com.example.gametalk.repository;

import com.example.gametalk.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

public interface PostRepository extends JpaRepository<Post, Long> {
    default Post findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post가 존재하지 않습니다."));
    }

    Page<Post> findAllByModifiedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
