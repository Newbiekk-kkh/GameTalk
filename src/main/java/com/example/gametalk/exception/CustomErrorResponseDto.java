package com.example.gametalk.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class CustomErrorResponseDto {
    private final HttpStatus httpStatus;
    private final String message;
}