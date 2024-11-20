package com.example.gametalk.exception.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ValidationErrorCode {
    EMAIL_DUPLICATED(HttpStatus.BAD_REQUEST, "중복된 이메일입니다."),
    EMAIL_BANNED(HttpStatus.BAD_REQUEST, "탈퇴처리 된 이메일은 다시 사용할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
