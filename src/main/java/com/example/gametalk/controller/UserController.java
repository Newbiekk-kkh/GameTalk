package com.example.gametalk.controller;

import com.example.gametalk.dto.UserRequestDto;
import com.example.gametalk.dto.UserResponseDto;
import com.example.gametalk.exception.authentication.AuthenticationException;
import com.example.gametalk.exception.validation.ValidationException;
import com.example.gametalk.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

    /**
     * @apiNote 회원가입
     * @param dto (이메일, 비밀번호, 이름)
     * @return UserResponseDto, HttpStatus.OK
     * @throws ValidationException
     */
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody @Valid UserRequestDto dto) throws ValidationException {
        UserResponseDto userResponseDto = userService.signUp(dto.getEmail(), dto.getPassword(), dto.getUsername());
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    // 회원탈퇴

    /**
     * @apiNote 로그인
     * @param dto (이메일, 패스워드)
     * @param request (HttpSession.getSession)
     * @return "로그인 완료" 문자열 반환 (HttpStatus.OK) / 이메일 또는 비밀번호가 일치하지 않을 시 예외 출력 (HttpStatus.BAD_REQUEST)
     * @throws AuthenticationException
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRequestDto dto, HttpServletRequest request) throws AuthenticationException {
        String successMessage = userService.authenticate(dto.getEmail(), dto.getPassword());
        HttpSession session = request.getSession(true);
        session.setAttribute("sessionKey", dto.getEmail());

        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
