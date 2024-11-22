package com.example.gametalk.controller;

import com.example.gametalk.dto.users.UserUpdateRequestDto;
import com.example.gametalk.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import com.example.gametalk.dto.users.UserRequestDto;
import com.example.gametalk.dto.users.UserResponseDto;
import com.example.gametalk.exception.authentication.AuthenticationException;
import com.example.gametalk.exception.validation.ValidationException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 프로필 조회 기능
    /**
     * @param id  유저 아이디
     * @return userProfileResponseDto (HttpStatus.OK) / 로그인한 유저와 조회유저가 다른 경우 예외 발생
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> findUserById(
            @PathVariable Long id,
            HttpServletRequest request) throws AuthenticationException {
        UserResponseDto userResponseDto = userService.findUserById(id);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    // 프로필 수정 기능
    /**
     * @param id  유저 아이디
     * @param dto  유저 요청 dto
     * @return userResponseDto (HttpStatus.OK) / 로그인한 유저와 조회유저가 다른 경우 입력값이 없는 경우 예외 발생
     */

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @Valid
            @RequestBody UserUpdateRequestDto dto,
            HttpServletRequest request) throws AuthenticationException, ValidationException {
        UserResponseDto userResponseDto = userService.updateUser(id, dto.getUsername(), dto.getEmail(), dto.getPassword(), dto.getNewPassword());
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    /**
     * @apiNote 회원가입
     * @param dto (이메일, 비밀번호, 이름)
     * @return UserResponseDto, HttpStatus.OK
     * @throws ValidationException (이메일, 비밀번호, 이름 검증에 관련된 예외 처리)
     */
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody @Valid UserRequestDto dto) throws ValidationException {
        UserResponseDto userResponseDto = userService.signUp(dto.getEmail(), dto.getPassword(), dto.getUsername());
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    /**
     * @param id  (유저 아이디)
     * @param dto (비밀번호)
     * @return "회원탈퇴 완료" 문자열 반환 (HttpStatus.OK)
     * @throws AuthenticationException (비밀번호가 일치하지 않거나 이미 탈퇴한 회원인 경우 예외 발생)
     */
    @PatchMapping("/users/{id}/deactive")
    public ResponseEntity<String> deactivateAccount(@PathVariable Long id, @RequestBody UserRequestDto dto) throws AuthenticationException {
        String successMessage = userService.deactivateAccount(id, dto.getPassword());
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    /**
     * @param dto (이메일, 패스워드)
     * @param request (HttpSession.getSession)
     * @return "로그인 완료" 문자열 반환 (HttpStatus.OK)
     * @throws AuthenticationException (이메일 또는 비밀번호가 일치하지 않을 시 예외 출력)
     * @apiNote 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRequestDto dto, HttpServletRequest request) throws AuthenticationException, ValidationException {
        String successMessage = userService.authenticate(dto.getEmail(), dto.getPassword());
        HttpSession session = request.getSession(true);
        session.setAttribute("sessionKey", dto.getEmail());

        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    /**
     * @apiNote 로그아웃
     * @param request (HttpSession.getSession) 로그인 된 세션이 있는 지 체크
     * @return "로그아웃 완료" 문자열 반환
     * @apiNote 로그아웃
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok("로그아웃 완료");
    }
}
