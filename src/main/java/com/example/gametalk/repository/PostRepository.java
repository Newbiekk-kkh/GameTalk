package com.example.gametalk.repository;

import com.example.gametalk.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface PostRepository  extends JpaRepository<Post, Long> {
    default Post findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    // 페이징 및 정렬 지원
    Page<Post> findAll(Pageable pageable);
}
