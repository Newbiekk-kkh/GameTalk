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
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final SessionUtils sessionUtils;

  
    public PostCreateResponseDto createPost(String title, Genre genre, String content) throws AuthenticationException {
        User findUser = userRepository.findByEmailOrElseThrow(sessionUtils.getLoginUserEmail());
        Post post = new Post(title, genre, content);
        post.setUser(findUser);
        postRepository.save(post);

        return new PostCreateResponseDto(post.getTitle(), post.getGenre(), post.getContent(), post.getCreatedAt(), post.getModifiedAt());
    }


    // 페이징 처리
    public Page<PostResponseDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostResponseDto::toDto);
    }

    public void delete(Long id) {
        Post findPost = postRepository.findByIdOrElseThrow(id);
        postRepository.delete(findPost);
    }

    public PostResponseDto findById(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);

        // NPE 방지
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
                findPost.getCreatedAt(),
                findPost.getModifiedAt()
        );
    }

    public PostResponseDto update(Long id, String title, Genre genre, String content) {
        Post findPost = postRepository.findByIdOrElseThrow(id);
        findPost.updatePost(title, genre, content);
        postRepository.save(findPost);

        return new PostResponseDto(
                findPost.getId(),
                findPost.getUser().getUsername(),
                findPost.getTitle(),
                findPost.getGenre(),
                findPost.getContent(),
                findPost.getCreatedAt(),
                findPost.getModifiedAt()
        );
    }
}
