package com.example.gametalk.service;

import com.example.gametalk.config.PasswordEncoder;
import com.example.gametalk.dto.users.login.LoginRequestDto;
import com.example.gametalk.dto.users.signup.SignupRequestDto;
import com.example.gametalk.dto.users.user.UserDeleteRequestDto;
import com.example.gametalk.dto.users.user.UserUpdateRequestDto;
import com.example.gametalk.dto.users.user.UserUpdateResponseDto;
import com.example.gametalk.dto.users.user.UserProfileResponseDto;
import com.example.gametalk.entity.User;
import com.example.gametalk.repository.UserRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    // 회원가입 기능
    public void signUp(SignupRequestDto signupRequestDto) {
        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());
        User user = new User(signupRequestDto.getUsername(), signupRequestDto.getEmail(), encodedPassword);
        userRepository.save(user);
    }

    // 로그인 기능
    public Long login(LoginRequestDto loginRequestDto) {
        // 아이디가 존재하는지 확인
        User user = userRepository.findByEmailOrElseThrow(loginRequestDto.getEmail());

        // 비밀번호가 일치하는지 확인
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 잘못되었습니다.");
        }
        return user.getId();
    }

    // 프로필 조회 기능
    public UserProfileResponseDto findUserById(Long userId) {
        User user = userRepository.findByIdOrElseThrow(userId);
        return User.toDto(user);
    }

    // 프로필 수정 기능
    public UserUpdateResponseDto updateUser(Long userId, Map<String, Object> updates) {
        User findUser = userRepository.findByIdOrElseThrow(userId);

        updates.forEach((key, value) -> {
            switch (key) {
                case "username":
                    findUser.setUsername((String) value);
                    break;
                case "email":
                    findUser.setEmail((String) value);
                    break;
                case "password":
                    String encodedPassword = passwordEncoder.encode((String) value);
                    findUser.setPassword((String) value);
                    break;
                default:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 입력값입니다 " + key);
            }
        });
        userRepository.save(findUser);
        return new UserUpdateResponseDto(findUser.getUsername(), findUser.getEmail());
    }

    // 회원탈퇴 기능
    public void deleteUser(Long userId, UserDeleteRequestDto userDeleteRequestDto) {
        // 사용자 확인
        User findUser = userRepository.findByIdOrElseThrow(userId);

        // 아이디가 존재하는지 확인
        User user = userRepository.findByEmailOrElseThrow(userDeleteRequestDto.getEmail());

        // 비밀번호가 일치하는지 확인
        if (!passwordEncoder.matches(userDeleteRequestDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 잘못되었습니다.");
        }

        userRepository.delete(findUser);
    }
}