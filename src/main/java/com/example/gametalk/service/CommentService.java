package com.example.gametalk.service;

import com.example.gametalk.dto.comment.CommentRequestDto;
import com.example.gametalk.dto.comment.CommentResponseDto;
import com.example.gametalk.entity.Comment;
import com.example.gametalk.entity.Post;
import com.example.gametalk.entity.User;
import com.example.gametalk.exception.authentication.AuthenticationErrorCode;
import com.example.gametalk.exception.authentication.AuthenticationException;
import com.example.gametalk.repository.CommentRepository;
import com.example.gametalk.repository.PostRepository;
import com.example.gametalk.repository.UserRepository;
import com.example.gametalk.utils.SessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final SessionUtils sessionUtils;

    // todo 댓글 작성
    public CommentResponseDto addComment(Long postId, CommentRequestDto dto) throws AuthenticationException {
        Post post = postRepository.findByIdOrElseThrow(postId);
        User findUser = userRepository.findByEmailOrElseThrow(sessionUtils.getLoginUserEmail());
        Comment comment = new Comment(findUser, post, dto.comment());
        Comment saveComment = commentRepository.save(comment);

        return CommentResponseDto.toDto(saveComment);
    }

    // todo 댓글 조회
    public List<CommentResponseDto> findAllComments(Long postId) throws AuthenticationException {
        Post post = postRepository.findByIdOrElseThrow(postId);
        return commentRepository.findByPostId(postId)
                .stream()
                .map(CommentResponseDto::toDto)
                .toList();
    }

    // todo 댓글 수정
    public CommentResponseDto updateComment(Long postId, Long commentId, CommentRequestDto dto) throws AuthenticationException {
        Post post = postRepository.findByIdOrElseThrow(postId);
        User findUser = userRepository.findByEmailOrElseThrow(sessionUtils.getLoginUserEmail());
        Comment comment = commentRepository.findCommentIdOrElseThrow(commentId);

        // 작성자 검증
        if (!comment.getUser().getId().equals(findUser.getId())) {
            throw new AuthenticationException(AuthenticationErrorCode.COMMENT_FORBIDDEN);
        }

        comment.setComment(dto.comment()); // 댓글 수정
        Comment updatedComment = commentRepository.save(comment); // 댓글 저장
        return CommentResponseDto.toDto(updatedComment);
    }

    // todo 댓글 삭제
    public void deleteComment(Long postId, Long commentId) throws AuthenticationException {
        Post post = postRepository.findByIdOrElseThrow(postId);
        Comment comment = commentRepository.findCommentIdOrElseThrow(commentId);
        commentRepository.delete(comment);
    }
}
