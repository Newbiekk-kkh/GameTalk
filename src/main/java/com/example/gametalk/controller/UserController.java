package com.example.gametalk.controller;

import com.example.gametalk.dto.users.UserProfileResponseDto;
import com.example.gametalk.dto.users.UserUpdateResponseDto;
import com.example.gametalk.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 프로필 조회 기능
    @GetMapping("/users/{id}")
    public ResponseEntity<UserProfileResponseDto> findUserById(@PathVariable Long id, HttpServletRequest request) {
        UserProfileResponseDto userProfileResponseDto = userService.findUserById(id);
        return new ResponseEntity<>(userProfileResponseDto, HttpStatus.OK);
    }

    // 프로필 수정 기능
    @PatchMapping("/users/{id}")
    public ResponseEntity<UserUpdateResponseDto> updateUser(
            @PathVariable Long id,
            @Valid
            @RequestBody Map<String, Object> updates,
            HttpServletRequest request) {
        return new ResponseEntity<>(userService.updateUser(id, updates), HttpStatus.OK);
    }
}