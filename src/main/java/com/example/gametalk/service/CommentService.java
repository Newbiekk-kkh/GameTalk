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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final SessionUtils sessionUtils;

    // 댓글 작성
    public CommentResponseDto addComment(Long postId, CommentRequestDto dto) throws AuthenticationException {
        Post post = postRepository.findByIdOrElseThrow(postId);

        User findUser = userRepository.findByEmailOrElseThrow(sessionUtils.getLoginUserEmail());
        if (!findUser.isActivated()) {
            throw new AuthenticationException(AuthenticationErrorCode.COMMENT_FORBIDDEN);
        }

        Comment comment = new Comment(findUser, post, dto.comment());
        Comment saveComment = commentRepository.save(comment);
        return CommentResponseDto.toDto(saveComment);
    }

    // 댓글 조회
    // 페이지네이션
    public Page<CommentResponseDto> findAllComments(Long postId, Pageable pageable){
        Post post = postRepository.findByIdOrElseThrow(postId);
        return commentRepository.findByPostId(postId, pageable)
                .map(CommentResponseDto::toDto);
    }

    // 댓글 수정
    public CommentResponseDto updateComment(Long postId, Long commentId, CommentRequestDto dto) throws AuthenticationException {
        //Post post = postRepository.findByIdOrElseThrow(postId);
        User findUser = userRepository.findByEmailOrElseThrow(sessionUtils.getLoginUserEmail());

        if (!findUser.isActivated()) {
            throw new AuthenticationException(AuthenticationErrorCode.COMMENT_FORBIDDEN);
        }

        Comment comment = commentRepository.findCommentIdOrElseThrow(commentId);
        //권한 확인
        String ownerEmail = comment.getUser().getEmail();
        sessionUtils.checkAuthorization(ownerEmail);
        // 댓글 수정
        comment.setComment(dto.comment());
        // 댓글 저장
        Comment updatedComment = commentRepository.save(comment);
        return CommentResponseDto.toDto(updatedComment);
    }

    // 댓글 삭제
    public void deleteComment(Long postId, Long commentId) throws AuthenticationException {
        //Post post = postRepository.findByIdOrElseThrow(postId);
        Comment comment = commentRepository.findCommentIdOrElseThrow(commentId);

        User findUser = userRepository.findByEmailOrElseThrow(sessionUtils.getLoginUserEmail());

        if (!findUser.isActivated()) {
            throw new AuthenticationException(AuthenticationErrorCode.COMMENT_FORBIDDEN);
        }

        //권한 확인
        String ownerEmail = comment.getUser().getEmail();
        sessionUtils.checkAuthorization(ownerEmail);
        // 댓글 삭제
        commentRepository.delete(comment);
    }
}
