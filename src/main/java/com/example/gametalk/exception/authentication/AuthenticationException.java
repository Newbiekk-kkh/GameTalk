package com.example.gametalk.exception.authentication;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthenticationException extends Exception{
    private final AuthenticationErrorCode errorCode;
}
