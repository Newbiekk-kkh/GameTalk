package com.example.gametalk.exception.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ValidationErrorCode {
    EMAIL_VALID(HttpStatus.BAD_REQUEST, "올바르지 않은 이메일 형식입니다."),
    EMAIL_DUPLICATED(HttpStatus.NOT_ACCEPTABLE, "중복된 이메일입니다."),
    PASSWORD_VALID(HttpStatus.BAD_REQUEST, "비밀번호는 최소 8자 이상이며, 대문자, 소문자, 숫자, 특수문자를 각각 최소 1개 이상 포함해야 합니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
