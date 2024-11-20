package com.example.gametalk.exception.validation;

import com.example.gametalk.exception.CustomErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        String errorMessage = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .orElse("Invalid input");

        CustomErrorResponseDto errorResponse = new CustomErrorResponseDto(HttpStatus.BAD_REQUEST, errorMessage);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    @ExceptionHandler(ValidationException.class)
    public ResponseEntity handleValidationException(ValidationException exception) {
        return ResponseEntity.status(exception.getErrorCode().getHttpStatus())
                .body(new CustomErrorResponseDto(exception.getErrorCode().getHttpStatus(), exception.getErrorCode().getMessage()));
    }
}
