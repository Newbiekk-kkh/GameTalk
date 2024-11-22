package com.example.gametalk.service;

import com.example.gametalk.dto.posts.PostCreateResponseDto;
import com.example.gametalk.dto.posts.PostResponseDto;
import com.example.gametalk.entity.Post;
import com.example.gametalk.entity.User;
import com.example.gametalk.enums.Genre;
import com.example.gametalk.exception.authentication.AuthenticationException;
import com.example.gametalk.repository.PostRepository;
import com.example.gametalk.repository.UserRepository;
import com.example.gametalk.utils.SessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final SessionUtils sessionUtils;

    /**
     *
     * @param title 제목
     * @param genre 장르
     * @param content 내용
     * @return 생성된 게시글 정보
     * @throws AuthenticationException 인증 실패 시 발생
     */
    public PostCreateResponseDto createPost(String title, Genre genre, String content) throws AuthenticationException {
        User findUser = userRepository.findByEmailOrElseThrow(sessionUtils.getLoginUserEmail());
        Post post = new Post(title, genre, content);
        post.setUser(findUser);
        postRepository.save(post);

        return new PostCreateResponseDto(post.getTitle(), post.getGenre(), post.getContent(), post.getCreatedAt(), post.getModifiedAt());
    }

    /**
     * 게시글 목록 조회
     * @param pageable 페이징 정보
     * @param startDate 조회 시작 날짜
     * @param endDate 조회 종료 날짜
     * @return 페이징 처리된 게시글 목록
     */
    public Page<PostResponseDto> findAll(Pageable pageable, String startDate, String endDate) {
        LocalDateTime start = null;
        LocalDateTime end = null;

        if (startDate != null) {
            start = LocalDateTime.parse(startDate + "T00:00:00");
        }
        if (endDate != null) {
            end = LocalDateTime.parse(endDate + "T23:59:59");
        }

        if (start != null && end != null) {
            return postRepository.findAllByModifiedAtBetween(start, end, pageable)
                    .map(PostResponseDto::toDto);
        } else {
            return postRepository.findAll(pageable)
                    .map(PostResponseDto::toDto);
        }
    }

    /**
     * 게시글 삭제
     * @param id 삭제할 게시글의 ID
     */
    public void delete(Long id) {
        Post findPost = postRepository.findByIdOrElseThrow(id);

        //권한 확인
        String userEmail = findPost.getUser().getEmail();
        sessionUtils.checkAuthorization(userEmail);

        postRepository.delete(findPost);
    }

    /**
     * 특정 게시글 조회
     * @param id 조회할 게시글의 ID
     * @return 조회된 게시글 정보
     */
    public PostResponseDto findById(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post가 존재하지 않습니다.");
        }

        Post findPost = optionalPost.get();

        return new PostResponseDto(
                findPost.getId(),
                findPost.getUser().getUsername(),
                findPost.getTitle(),
                findPost.getGenre(),
                findPost.getContent(),
                findPost.getLikes().size(),
                findPost.getCreatedAt(),
                findPost.getModifiedAt()
        );
    }

    /**
     * 특정 게시글 수정
     * @param id 수정할 게시글의 ID
     * @param title 제목
     * @param genre 장르
     * @param content 내용
     * @return 수정된 게시글의 정보
     * @throws AuthenticationException 인증 실패 시 발생
     */
    public PostResponseDto update(Long id, String title, Genre genre, String content) throws AuthenticationException {
        Post findPost = postRepository.findByIdOrElseThrow(id);

        //권한 확인
        String userEmail = findPost.getUser().getEmail();
        sessionUtils.checkAuthorization(userEmail);

        findPost.updatePost(title, genre, content);
        postRepository.save(findPost);

        return new PostResponseDto(
                findPost.getId(),
                findPost.getUser().getUsername(),
                findPost.getTitle(),
                findPost.getGenre(),
                findPost.getContent(),
                findPost.getLikes().size(),
                findPost.getCreatedAt(),
                findPost.getModifiedAt()
        );
    }
}
