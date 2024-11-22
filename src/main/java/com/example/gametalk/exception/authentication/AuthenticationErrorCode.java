package com.example.gametalk.exception.authentication;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthenticationErrorCode {
    EMAIL_INCORRECT(HttpStatus.BAD_REQUEST, "이메일이 일치하지 않습니다."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 이메일입니다."),
    PASSWORD_INCORRECT(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    USER_DEACTIVATED(HttpStatus.NOT_FOUND, "이미 회원탈퇴 처리 되었습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다."),
    COMMENT_FORBIDDEN(HttpStatus.FORBIDDEN, "댓글에 대한 접근 권한이 없습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
