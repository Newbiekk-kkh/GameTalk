package com.example.gametalk.controller;

import com.example.gametalk.dto.posts.PostCreateResponseDto;
import com.example.gametalk.dto.posts.PostRequestDto;
import com.example.gametalk.dto.posts.PostResponseDto;
import com.example.gametalk.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostCreateResponseDto> createPost(@RequestBody PostRequestDto requestDto) {

        PostCreateResponseDto postCreateResponseDto =
            postService.createPost(
                    requestDto.getTitle(),
                    requestDto.getGenre(),
                    requestDto.getContent()
            );

        return  new ResponseEntity<>(postCreateResponseDto, HttpStatus.CREATED);
    }

    // 정렬, 페이지네이션
    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> findAll(
            @RequestParam(defaultValue = "0") int page,      // 기본값: 첫 페이지 (0)
            @RequestParam(defaultValue = "10") int size      // 기본값: 한 페이지에 10개
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<PostResponseDto> postResponseDtoPage = postService.findAll(pageable);

        return ResponseEntity.ok(postResponseDtoPage);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> findById(@PathVariable Long id) {

        PostResponseDto postResponseDto = postService.findById(id);

        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> update(@PathVariable Long id, @RequestBody PostRequestDto dto) {
        PostResponseDto updatedPost = postService.update(id, dto.getTitle(), dto.getGenre(), dto.getContent());
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        postService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
