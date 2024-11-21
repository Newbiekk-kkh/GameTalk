package com.example.gametalk.repository;

import com.example.gametalk.entity.Comment;
import com.example.gametalk.exception.authentication.AuthenticationErrorCode;
import com.example.gametalk.exception.authentication.AuthenticationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    //특정 게시물(postId)의 모든 댓글 조회
    List<Comment> findByPostId(Long postId);

    //commentId로 댓글 조회
    default Comment findCommentIdOrElseThrow(long id) throws AuthenticationException {
        return findById(id).orElseThrow(()-> new AuthenticationException(AuthenticationErrorCode.COMMENT_NOT_FOUND));
    }
}