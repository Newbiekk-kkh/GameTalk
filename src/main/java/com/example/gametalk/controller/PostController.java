package com.example.gametalk.controller;

import com.example.gametalk.dto.posts.PostCreateResponseDto;
import com.example.gametalk.dto.posts.PostRequestDto;
import com.example.gametalk.dto.posts.PostResponseDto;
import com.example.gametalk.exception.authentication.AuthenticationException;
import com.example.gametalk.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 게시글 관리 controller
 */
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     *
     * @param requestDto 게시글 생성 요청 데이터
     * @return 생성된 게시글 정보, HttpStatus.CREATED
     * @throws AuthenticationException
     */
    @PostMapping
    public ResponseEntity<PostCreateResponseDto> createPost(@RequestBody PostRequestDto requestDto) throws AuthenticationException {

        PostCreateResponseDto postCreateResponseDto =
                postService.createPost(
                        requestDto.getTitle(),
                        requestDto.getGenre(),
                        requestDto.getContent()
                );

        return new ResponseEntity<>(postCreateResponseDto, HttpStatus.CREATED);

    }
    
    /**
     * 게시글 전체 조회
     * @param page 페이지 번호 (기본값-0)
     * @param size 페이지 크기 (기본값-10)
     * @param sortBy 정렬 기준((modifiedAt, likes, createdAt)
     * @param startDate 조회 시작 날짜
     * @param endDate 조회 종료 날짜
     * @return 페이징 처리 된 게시글 목록, HttpStatus.OK
     */
    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {
        Pageable pageable;

        if ("modifiedAt".equals(sortBy)) {
            pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("modifiedAt")));
        } else if ("likes".equals(sortBy)) {
            pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("likes"))); // 기본값 생성일자
        } else {
            pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        }

        Page<PostResponseDto> postResponseDtoPage = postService.findAll(pageable, startDate, endDate);
        return ResponseEntity.ok(postResponseDtoPage);
    }

    /**
     * 특정 게시글 조회
     * @param id 조회할 게시글의 ID
     * @return 게시글 정보, HttpStatus.OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> findById(@PathVariable Long id) {
        PostResponseDto postResponseDto = postService.findById(id);
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }

    /**
     * 게시글 수정
     * @param id 수정할 게시글의 ID
     * @param dto 게시글 수정 요청 데이터
     * @return 수정된 게시글 정보, HttpStatus.OK
     * @throws AuthenticationException
     */
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> update(@PathVariable Long id, @RequestBody PostRequestDto dto) throws AuthenticationException {
        PostResponseDto updatedPost = postService.update(id, dto.getTitle(), dto.getGenre(), dto.getContent());
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    /**
     * 게시글 삭제
     * @param id 삭제할 게시글의 ID
     * @return 삭제 성공 시 HttpStatus.OK
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
