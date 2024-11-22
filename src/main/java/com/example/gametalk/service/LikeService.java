package com.example.gametalk.service;

import com.example.gametalk.dto.likes.LikeResponseDto;
import com.example.gametalk.entity.LikePost;
import com.example.gametalk.entity.Post;
import com.example.gametalk.entity.User;
import com.example.gametalk.exception.authentication.AuthenticationException;
import com.example.gametalk.repository.LikePostRepository;
import com.example.gametalk.repository.PostRepository;
import com.example.gametalk.repository.UserRepository;
import com.example.gametalk.utils.SessionUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikePostRepository likePostRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final SessionUtils sessionUtils;

    @Transactional
    public String likePost(Long postId) throws AuthenticationException {
        User loginUser = userRepository.findByEmailOrElseThrow(sessionUtils.getLoginUserEmail());
        Post post = postRepository.findByIdOrElseThrow(postId);

        isMyPost(loginUser, post);

        LikePost likedPost = new LikePost();
        likedPost.setUser(loginUser);
        likedPost.setPost(post);

        isAlreadyLiked(likedPost);

        likePostRepository.save(likedPost);
        return "좋아요를 눌렀습니다.";
    }

    @Transactional
    public String cancelLikedPost(Long postId) throws AuthenticationException {
        User loginUser = userRepository.findByEmailOrElseThrow(sessionUtils.getLoginUserEmail());
        Post LikedPost = postRepository.findByIdOrElseThrow(postId);

        LikePost findLikedPost = likePostRepository.findByUserAndPostOrElseThrow(loginUser, LikedPost);

        likePostRepository.delete(findLikedPost);

        return "좋아요가 취소되었습니다.";
    }

    public void isAlreadyLiked(LikePost likePost) {
        Optional<LikePost> findLikedPost = likePostRepository.findByUserAndPost(likePost.getUser(), likePost.getPost());
        if (findLikedPost.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 좋아요를 누른 게시물입니다.");
        }
    }

    public void isMyPost(User user, Post post) {
        if (Objects.equals(user.getId(), post.getUser().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "본인의 게시물에는 좋아요를 누를 수 없습니다.");
        }
    }

    public LikeResponseDto viewNumberOfLikes(Long postId) {
        Long numberOfLikes = likePostRepository.countByPostId(postId);
        return new LikeResponseDto(postId, numberOfLikes);
    }
}
