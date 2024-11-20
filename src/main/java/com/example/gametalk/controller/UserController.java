package com.example.gametalk.controller;

import com.example.gametalk.common.Const;
import com.example.gametalk.dto.users.login.LoginRequestDto;
import com.example.gametalk.dto.users.signup.SignupRequestDto;
import com.example.gametalk.dto.users.user.UserDeleteRequestDto;
import com.example.gametalk.dto.users.user.UserProfileResponseDto;
import com.example.gametalk.dto.users.user.UserUpdateResponseDto;
import com.example.gametalk.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입 기능
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.signUp(requestDto);
        return new ResponseEntity<>("회원가입이 성공하였습니다", HttpStatus.CREATED);
    }

    // 로그인 기능
    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequestDto requestDto, HttpServletRequest request) {
        // 로그인 로직 호출
        Long userId = userService.login(requestDto);

        // 로그인 성공 시 세션생성 및 사용자 정보 저장
        HttpSession session = request.getSession();
        session.setAttribute(Const.LOGIN_USER_ID, userId); // 세션 키 상수 관리

        return "redirect:/post";
    }

    // 로그아웃 기능
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        // 세션 무효화
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/login";
    }

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

    // 회원탈퇴 기능
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id,
            @Valid
            @RequestBody UserDeleteRequestDto requestDto,
            HttpServletRequest request) {

        // 회원탈퇴 시 아이디 및 비밀번호 검증 로직
        userService.deleteUser(id, requestDto);

        return new ResponseEntity<>("회원탈퇴 완료", HttpStatus.OK);
    }
}