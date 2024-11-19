package com.example.gametalk.controller;

import com.example.gametalk.dto.UserRequestDto;
import com.example.gametalk.dto.UserResponseDto;
import com.example.gametalk.exception.authentication.AuthenticationException;
import com.example.gametalk.exception.validation.ValidationException;
import com.example.gametalk.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody @Valid UserRequestDto dto) throws ValidationException {
        UserResponseDto userResponseDto = userService.signUp(dto.getEmail(), dto.getPassword(), dto.getUsername());
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    //로그인


    //로그아웃
}
