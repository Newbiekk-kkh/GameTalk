package com.example.gametalk.exception.validation;

import com.example.gametalk.exception.CustomErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity handleValidationException(ValidationException exception) {
        return ResponseEntity.status(exception.getErrorCode().getHttpStatus())
                .body(new CustomErrorResponseDto(exception.getErrorCode().getHttpStatus(), exception.getErrorCode().getMessage()));
    }
}
