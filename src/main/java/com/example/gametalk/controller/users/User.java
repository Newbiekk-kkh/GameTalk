package com.example.gametalk.controller.users;

import com.example.gametalk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class User {

    private final UserService userService;

    // todo 회원가입 기능
    @PostMapping("/signup")

    // todo 로그인 기능
    @PostMapping("/login")

    // todo  로그아웃 기능
    @PostMapping("/logout")

    // todo 프로필 조회 기능
    @GetMapping("/users/{id}")

    // todo 프로필 수정 기능
    @PutMapping("/users/{id}")

    // todo 회원탈퇴 기능
    @DeleteMapping("/users/{id}")
}
