package com.example.gametalk.service;

import com.example.gametalk.dto.users.UserUpdateResponseDto;
import com.example.gametalk.dto.users.UserProfileResponseDto;
import com.example.gametalk.entity.User;
import com.example.gametalk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
                    // todo 패스워드 암호화 기능 연동 필요
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
}