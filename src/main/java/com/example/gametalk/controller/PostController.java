package com.example.gametalk.controller;

import com.example.gametalk.dto.posts.PostCreateResponseDto;
import com.example.gametalk.dto.posts.PostRequestDto;
import com.example.gametalk.dto.posts.PostResponseDto;
import com.example.gametalk.service.PostService;
import lombok.RequiredArgsConstructor;
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
                    requestDto.getUsername(),
                    requestDto.getTitle(),
                    requestDto.getGenre(),
                    requestDto.getContent()
            );

        return  new ResponseEntity<>(postCreateResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> findAll(){

        List<PostResponseDto> postResponseDtoList = postService.findAll();

        return new ResponseEntity<>(postResponseDtoList, HttpStatus.OK);
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
