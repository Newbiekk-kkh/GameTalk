package com.example.gametalk.exception.authentication;

import com.example.gametalk.exception.CustomErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthenticationExceptionHandler {
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity handleAuthenticationException(AuthenticationException exception) {
        return ResponseEntity.status(exception.getErrorCode().getHttpStatus())
                .body(new CustomErrorResponseDto(exception.getErrorCode().getHttpStatus(), exception.getErrorCode().getMessage()));
    }
}
