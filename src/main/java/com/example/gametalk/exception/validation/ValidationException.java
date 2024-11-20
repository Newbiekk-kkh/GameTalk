package com.example.gametalk.exception.validation;

import com.example.gametalk.exception.authentication.AuthenticationErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ValidationException extends Exception {
    private final ValidationErrorCode errorCode;


}
